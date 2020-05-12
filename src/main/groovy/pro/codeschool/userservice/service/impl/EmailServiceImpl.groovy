package pro.codeschool.userservice.service.impl

import groovy.text.Template
import groovy.text.TemplateEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.service.EmailService

import javax.annotation.PostConstruct
import javax.mail.Message
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service()
class EmailServiceImpl implements EmailService {

    String port
    String host
    @Value('${api.basePath.users}')
    String basePath
    @Value('${email.from}')
    String fromAddress
    @Value('${email.subject}')
    String subject
    @Value('${email.footer}')
    String footer
    @Value('${email.product}')
    String product

    @Autowired
    Session session
    @Autowired
    TemplateEngine templateEngine

    Template emailTemplate

    @PostConstruct
    void init() {
        emailTemplate = templateEngine.createTemplate(getClass().getResourceAsStream('/templates/signup-email.html').text)
    }

    @Override
    void sendEmail(User user) {
        String email = emailTemplate.make([
            'fullName': [user.firstName, user.lastName]?.findAll()?.join(' '),
            'registrationLink': "http://localhost:8080/api/${basePath}/validate/${user.id}/${user.token}",
            'footer': footer?.trim(),
            'product': product?.trim()
        ]).toString()
        send(email, user.email)
    }

     void send(String emailContent, String emailTo) {

         MimeMessage mimeMessage = new MimeMessage(session)
         mimeMessage.setSubject(subject, "UTF-8")
         mimeMessage.setText(emailContent, "UTF-8", "html")

         mimeMessage.with {
             from = new InternetAddress(fromAddress, fromAddress)
             replyTo = InternetAddress.parse(fromAddress, false)
             sentDate = new Date()
          }

         mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo?.trim(), false))

         Transport.send(mimeMessage)
    }
}
