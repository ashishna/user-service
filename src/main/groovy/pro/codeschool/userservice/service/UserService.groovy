package pro.codeschool.userservice.service

import pro.codeschool.userservice.api.model.User

interface UserService {

    User createUser(User user)
}