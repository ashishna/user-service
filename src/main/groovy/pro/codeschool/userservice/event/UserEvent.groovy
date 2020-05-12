package pro.codeschool.userservice.event

import org.springframework.context.ApplicationEvent

class UserEvent extends ApplicationEvent {

    String name
    UserEvent(Object source, String name) {
        super(source)
        this.name = name
    }
}
