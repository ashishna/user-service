package pro.codeschool.userservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import pro.codeschool.userservice.api.model.ApiError
import pro.codeschool.userservice.service.UserService
import pro.codeschool.userservice.service.UserServiceImpl

@Configuration
class AppConfig {

    @Bean()
    @Scope("prototype")
    ApiError apiError() {
        return new ApiError()
    }

    @Bean
    UserService userService() {
        return new UserServiceImpl()
    }
}
