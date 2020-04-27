package pro.codeschool.userservice.repository

import org.springframework.data.repository.CrudRepository
import pro.codeschool.userservice.entity.UserEntity

interface UserRepository extends CrudRepository<UserEntity, Long> {
}
