package pro.codeschool.userservice.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pro.codeschool.userservice.api.model.Password
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.entity.PasswordReset
import pro.codeschool.userservice.entity.UserEntity
import pro.codeschool.userservice.entity.UserTokenEntity
import pro.codeschool.userservice.error.TokenNotFoundException
import pro.codeschool.userservice.error.UserNotFoundException
import pro.codeschool.userservice.error.UserServiceException
import pro.codeschool.userservice.event.UserEventPublisher
import pro.codeschool.userservice.repository.TokenRepository
import pro.codeschool.userservice.repository.UserRepository
import pro.codeschool.userservice.service.UserService
import pro.codeschool.userservice.service.helper.UserServiceHelper

import javax.transaction.Transactional
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository

    @Autowired
    UserEventPublisher eventPublisher

    @Autowired
    TokenRepository tokenRepository

    @Transactional
    User createUser(User user) {
        UserTokenEntity token = UserServiceHelper.generateToken()
        token.createdBy = 'API_USER'
        token.modifiedBy = 'API_USER'
        UserEntity userEntity = userRepository.save(
                new UserEntity(
                        lastName: user.lastName,
                        firstName: user.firstName,
                        emailAddress: user.email,
                        password: user.password,
                        createdBy: 'API_USER',
                        userTokens: [ token ].toSet()),
        )
        user.id = userEntity.id
        user.currentToken = token.token
        eventPublisher.publishUserRegisteredEvent(user, "${user.firstName} created")
        return user
    }


    @Override
    User getUser(Long id) {
        UserEntity userEntity = getUserEntity(id)
        return UserServiceHelper.transform(userEntity)
    }


    @Override
    List<User> getAll() {
        return userRepository.findAll()?.collect {
            return UserServiceHelper.transform(it)
        }
    }

    User validate(Long id, String token) {
        UserEntity userEntity = getUserEntity(id)
        UserTokenEntity userToken = userEntity.userTokens?.find { it.token == token && !it.isExpired }
        if(!userToken) {
            throw new TokenNotFoundException("Token not found or already expired")
        }

        //Invalidate the token
        userToken.isExpired = true
        //Enable the user
        userEntity.isEnabled = true
        userEntity.isValidated = true
        userRepository.save(userEntity)
        return UserServiceHelper.transform(userEntity)
    }

    @Override
    void resendToken(Long userId) {
        UserEntity userEntity = getUserEntity(userId)
        if(userEntity.isValidated && userEntity.isEnabled) {
            throw new UserServiceException("User already enabled and validated")
        }
        userEntity.userTokens.add(UserServiceHelper.generateToken())
        userRepository.save(userEntity)
        User user = UserServiceHelper.transform(userEntity)
        eventPublisher.publishUserRegisteredEvent(user, "${user.firstName} token resent")
    }

    @Override
    void generateResetPasswordToken(Password password) {
        UserEntity userEntity = userRepository.findByEmail(password.uId)
        if(!userEntity) {
            throw new UserNotFoundException(password.uId)
        }

        //Invalidate existing password reset tokens
        userEntity?.passwordResets?.findAll {!it.isExpired}?.each {
            it.isExpired = true
        }
        //Add new token
        PasswordReset passwordReset = new PasswordReset(
                user: userEntity,
                token: UUID.randomUUID().toString(),
                dateTime: LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond(),
                isExpired: false,
                isUsed: false
        )
        userEntity?.passwordResets << passwordReset
        //Update the user
        userRepository.save(userEntity)
        User user = UserServiceHelper.transform(userEntity)
        eventPublisher.publishUserRegisteredEvent(user, "${user.firstName} password rest")
    }

    void resetPassword(User user, Long userId) {
        UserEntity userEntity = getUserEntity(userId)

        //find the token
        PasswordReset passwordReset = userEntity?.passwordResets?.find {it.token == user.currentToken && !it.isExpired && !it.isUsed}
        if(!passwordReset) {
            throw new TokenNotFoundException("Token ${user.currentToken} not found or expired")
        }
        passwordReset.isExpired = true
        passwordReset.isUsed = true
        userEntity.password = user.password

        //Update the user
        userRepository.save(userEntity)
    }

    private UserEntity getUserEntity(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow({ ->
                    new UserNotFoundException("${id}")
                })
        return userEntity
    }
}
