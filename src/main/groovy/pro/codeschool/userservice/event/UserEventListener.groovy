package pro.codeschool.userservice.event

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.service.EmailService

@Component
class UserEventListener {

    @Autowired
    EmailService emailService

    @Async
    @EventListener
    void handleUserEvent(UserEvent userEvent) {

        if(userEvent instanceof UserRegisteredEvent) {
            handleUserRegisteredEvent(userEvent.getSource())
        }
    }

    private void handleUserRegisteredEvent(Object source) {
        emailService.sendEmail((User)source)
    }

}
