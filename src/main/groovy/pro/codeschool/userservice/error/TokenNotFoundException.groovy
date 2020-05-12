package pro.codeschool.userservice.error

class TokenNotFoundException extends UserServiceException {

    TokenNotFoundException(String message ) {
        super(message)
    }
}
