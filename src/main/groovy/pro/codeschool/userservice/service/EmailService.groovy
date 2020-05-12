package pro.codeschool.userservice.service

import pro.codeschool.userservice.api.model.User

interface EmailService {

    void sendEmail(User user)
}