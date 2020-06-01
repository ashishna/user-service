package pro.codeschool.userservice.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pro.codeschool.userservice.api.model.User
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
    User getUser(long id) {
        UserEntity userEntity = getUserEntity(id)
        return UserServiceHelper.transform(userEntity)
    }


    @Override
    List<User> getAll() {
        return userRepository.findAll()?.collect {
            return UserServiceHelper.transform(it)
        }
    }

    User validate(long id, String token) {
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
    void resendToken(long userId) {
        UserEntity userEntity = getUserEntity(userId)
        if(userEntity.isValidated && userEntity.isEnabled) {
            throw new UserServiceException("User already enabled and validated")
        }
        userEntity.userTokens.add(UserServiceHelper.generateToken())
        userRepository.save(userEntity)
        User user = UserServiceHelper.transform(userEntity)
        eventPublisher.publishUserRegisteredEvent(user, "${user.firstName} token resent")
    }

     private UserEntity getUserEntity(long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow({ ->
                    new UserNotFoundException("${id} not found")
                })
        return userEntity
    }
}
