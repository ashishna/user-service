package pro.codeschool.userservice.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.entity.UserEntity
import pro.codeschool.userservice.entity.UserTokenEntity
import pro.codeschool.userservice.error.TokenNotFoundException
import pro.codeschool.userservice.error.UserNotFoundException
import pro.codeschool.userservice.event.UserEventPublisher
import pro.codeschool.userservice.repository.UserRepository
import pro.codeschool.userservice.service.UserService

import javax.transaction.Transactional
import java.time.LocalDateTime

@Service
class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository

    @Autowired
    UserEventPublisher eventPublisher

    @Transactional
    User createUser(User user) {
        String token = UUID.randomUUID().toString()
        UserEntity userEntity = userRepository.save(
                new UserEntity(
                        lastName: user.lastName,
                        firstName: user.firstName,
                        emailAddress: user.email,
                        password: user.password,
                        createdBy: 'API_USER',
                        userTokens: [
                                new UserTokenEntity(
                                        token: token,
                                        isExpired: false,
                                        dateTime: LocalDateTime.now().toString())
                        ].toSet()),
        )
        user.id = userEntity.id
        user.token = token
        eventPublisher.publishUserRegisteredEvent(user, "${user.firstName} created")
        return user
    }

    @Override
    User getUser(long id) {
        UserEntity userEntity = getUserEntity(id)
        return transform(userEntity)
    }

    private UserEntity getUserEntity(long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow({ ->
                    new UserNotFoundException("User ${id} not found")
                })
        userEntity
    }

    @Override
    List<User> getAll() {
        return userRepository.findAll()?.collect {
            return transform(it)
        }
    }

    User validate(long id, String token) {
        UserEntity userEntity = getUserEntity(id)
        UserTokenEntity userToken = userEntity.userTokens?.find {it.token == token && !it.isExpired}
        if(!userToken) {
            throw new TokenNotFoundException("Token not found or already expired")
        }
        //Invalidate the token
        userToken.isExpired = true
        //Enable the user
        userEntity.isEnabled = true
        userEntity.isValidated = true
        userRepository.save(userEntity)
        return transform(userEntity)
    }

    private static User transform(UserEntity userEntity) {
        return new User(id: userEntity.id,
                firstName: userEntity.firstName,
                lastName: userEntity.lastName,
                email: userEntity.emailAddress,
                token: userEntity.userTokens?.find()
        )
    }
}
