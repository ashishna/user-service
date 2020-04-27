package pro.codeschool.userservice.error

class UserException extends RuntimeException {

    UserException(String message) {
        super(message)
    }
}
