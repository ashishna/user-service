package pro.codeschool.userservice.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class UserEventPublisher {

    private final ApplicationEventPublisher publisher;

    UserEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    void publishUserRegisteredEvent(Object object, final String name) {
        publisher.publishEvent(new UserRegisteredEvent(object, name));
    }

}
