package pro.codeschool.userservice.error

class UserServiceException extends RuntimeException {

    UserServiceException(String message) {
        super(message)
    }
}
