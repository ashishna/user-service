package pro.codeschool.userservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class UserServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(UserServiceApplication, args)
    }

}
