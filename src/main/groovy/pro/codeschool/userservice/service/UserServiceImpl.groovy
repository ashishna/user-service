package pro.codeschool.userservice.service

import org.springframework.beans.factory.annotation.Autowired
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.entity.UserEntity
import pro.codeschool.userservice.entity.UserTokenEntity
import pro.codeschool.userservice.repository.UserRepository

import javax.transaction.Transactional
import java.time.LocalDateTime

class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository

    @Transactional
    User createUser(User user) {
        UserEntity userEntity = userRepository.save(
            new UserEntity(
                lastName: user.lastName,
                firstName: user.firstName,
                emailAddress: user.email,
                password: user.password,
                createdBy: 'API_USER',
                userTokens: [
                    new UserTokenEntity(
                        token: UUID.randomUUID().toString(),
                        isExpired: false,
                        dateTime: LocalDateTime.now().toString())
                ].toSet()),
        )
        user.id = userEntity.id
        user.token = userEntity.userTokens.find {!it.isExpired}.token
        return user
    }

}
