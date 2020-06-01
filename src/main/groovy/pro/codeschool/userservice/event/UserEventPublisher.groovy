package pro.codeschool.userservice.event

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class UserEventPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(UserEventPublisher)

    private final ApplicationEventPublisher publisher;

    UserEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    void publishUserRegisteredEvent(Object object, final String name) {
        LOG.info("Publishing user created event")
        publisher.publishEvent(new UserRegisteredEvent(object, name));
    }

}
