package pro.codeschool.userservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = 'email', ignoreUnknownFields = true)
class MailConfigProperties {

    String host
    String user
    String password
    String from
    int port

}
