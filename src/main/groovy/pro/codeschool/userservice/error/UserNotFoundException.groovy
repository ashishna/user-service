package pro.codeschool.userservice.error

class UserNotFoundException extends UserException {

    UserNotFoundException(String id) {
        super("The user with ${id} not found")
    }
}
