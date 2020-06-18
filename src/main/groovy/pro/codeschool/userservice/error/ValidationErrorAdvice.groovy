package pro.codeschool.userservice.error

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import pro.codeschool.userservice.api.model.ApiError
import pro.codeschool.userservice.utils.DateUtils

import java.time.LocalDateTime

@RestControllerAdvice
class ValidationErrorAdvice {

    @Autowired
    ApiError apiError

    /**
     * Exception thrown when JSR-303 validations fails.
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleError(MethodArgumentNotValidException ex) {
        apiError.with {
            code = HttpStatus.BAD_REQUEST.value()
            type ='ValidationException'
            errors = ex?.bindingResult?.allErrors?.collect { "${it.field} - ${it.defaultMessage}".toString() }
            timestamp = DateUtils.now()
        }
        return apiError
    }

    @ExceptionHandler(UserServiceException)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError notFound(Exception ex) {
        apiError.with {
            code = HttpStatus.NOT_FOUND.value()
            type ='UserServiceException'
            errors = [ex.message ]
            timestamp = DateUtils.now()
        }
        return apiError
    }

    @ExceptionHandler(DataIntegrityViolationException)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError dataAccess(DataIntegrityViolationException ex) {
            apiError.with {
                code = HttpStatus.BAD_REQUEST.value()
                type ='UserAlreadyExistException'
                errors = ['The user is already registered ' ]
                timestamp = LocalDateTime.now()
            }
        return apiError
    }

    @ExceptionHandler(RuntimeException)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiError handleRuntime(RuntimeException ex) {
        apiError.with {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value()
            type ='RuntimeError'
            errors = ['An unexpected error has occurred ' + ex ]
            timestamp = DateUtils.now()
        }
        return apiError
    }

    @ExceptionHandler(Exception)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiError all(Exception ex) {
        apiError.with {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value()
            type ='InternalError'
            errors = ['An exception has occurred' ]
            timestamp = DateUtils.now()
        }
        return apiError
    }
}
