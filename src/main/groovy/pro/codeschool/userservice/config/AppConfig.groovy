package pro.codeschool.userservice.config

import groovy.text.SimpleTemplateEngine
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import pro.codeschool.userservice.api.model.ApiError

import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session

@Configuration
class AppConfig {

    @Value('${email.host}')
    String mailHost
    @Value('${email.port}')
    String mailPort
    @Value('${email.user}')
    String userName
    @Value('${email.password}')
    String password

    @Bean()
    @Scope("prototype")
    ApiError apiError() {
        return new ApiError()
    }

    @Bean
    SimpleTemplateEngine templateEngine() {
        return new SimpleTemplateEngine()
    }

    @Bean
    Session session() {
        Properties props = new Properties()
        props.put("mail.smtp.host", mailHost)
        props.put("mail.smtp.socketFactory.port", mailPort)
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.port", mailPort)

        return Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password)
            }
        })
    }
}
