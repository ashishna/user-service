package pro.codeschool.userservice.service

import pro.codeschool.userservice.api.model.User

interface UserService {

    User createUser(User user)
    User getUser(long id)
    List<User> getAll()
    User validate(long id, String token)
    void resendToken(long id)
}