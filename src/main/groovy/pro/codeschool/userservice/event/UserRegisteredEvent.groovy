package pro.codeschool.userservice.event

class UserRegisteredEvent extends UserEvent {

    UserRegisteredEvent(Object source, String name) {
        super(source, name)
    }
}
