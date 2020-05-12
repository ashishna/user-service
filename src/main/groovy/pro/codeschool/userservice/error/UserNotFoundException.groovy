package pro.codeschool.userservice.error

class UserNotFoundException extends UserServiceException {

    UserNotFoundException(String id) {
        super("The user with ${id} not found")
    }
}
