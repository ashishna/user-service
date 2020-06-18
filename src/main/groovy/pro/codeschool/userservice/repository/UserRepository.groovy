package pro.codeschool.userservice.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import pro.codeschool.userservice.entity.PasswordReset
import pro.codeschool.userservice.entity.UserEntity

interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT U FROM USERS U WHERE U.emailAddress = ?1")
    UserEntity findByEmail(String email)

}
