package pro.codeschool.userservice.service.helper


import org.springframework.stereotype.Component
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.entity.UserEntity
import pro.codeschool.userservice.entity.UserTokenEntity
import pro.codeschool.userservice.utils.DateUtils

@Component
class UserServiceHelper {

    static UserTokenEntity generateToken() {
        new UserTokenEntity(
                token: UUID.randomUUID().toString(),
                isExpired: false,
                tokenExpiry: DateUtils.tomorrow())
    }

     static User transform(UserEntity userEntity) {
        return new User(id: userEntity.id,
                firstName: userEntity.firstName,
                lastName: userEntity.lastName,
                email: userEntity.emailAddress,
                currentToken: userEntity.userTokens?.max {it.tokenExpiry}?.token
        )
    }

}
