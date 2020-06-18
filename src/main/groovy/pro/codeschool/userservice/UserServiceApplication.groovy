package pro.codeschool.userservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAsync
@EnableScheduling
class UserServiceApplication {


    static void main(String[] args) {
        Properties props = System.getProperties();
        props.setProperty("jasypt.encryptor.password", "password");
        SpringApplication.run(UserServiceApplication, args)
    }

}
