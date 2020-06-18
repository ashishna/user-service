package pro.codeschool.userservice.service

import pro.codeschool.userservice.api.model.Password
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.entity.UserEntity

interface UserService {

    User createUser(User user)
    User getUser(Long id)
    List<User> getAll()
    User validate(Long id, String token)
    void resendToken(Long id)
    void generateResetPasswordToken(Password password)
    void resetPassword(User user, Long id)
}