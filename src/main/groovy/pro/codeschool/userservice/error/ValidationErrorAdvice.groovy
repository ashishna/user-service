package pro.codeschool.userservice.error

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import pro.codeschool.userservice.api.model.ApiError

import java.time.LocalDateTime

@RestControllerAdvice
class ValidationErrorAdvice {

    @Autowired
    ApiError apiError

    @ExceptionHandler(MethodArgumentNotValidException)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleError(MethodArgumentNotValidException ex) {
        apiError.with {
            code = HttpStatus.BAD_REQUEST.value()
            type ='ValidationException'
            errors = ex?.bindingResult?.allErrors?.collect {it.defaultMessage}
            timestamp = LocalDateTime.now()
        }
        return apiError
    }

    @ExceptionHandler(UserNotFoundException)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError notFound(Exception ex) {
        apiError.with {
            code = HttpStatus.NOT_FOUND.value()
            type ='UserNotFoundException'
            errors = [ex.message ]
            timestamp = LocalDateTime.now()
        }
        return apiError
    }

    @ExceptionHandler(DataIntegrityViolationException)
    @ResponseStatus(HttpStatus.NOT_FOUND)
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiError handleRuntime(RuntimeException ex) {
        apiError.with {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value()
            type ='RuntimeError'
            errors = ['An unexpected error has occurred ' + ex ]
            timestamp = LocalDateTime.now()
        }
        return apiError
    }

    @ExceptionHandler(Exception)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiError all(Exception ex) {
        apiError.with {
            code = HttpStatus.NOT_FOUND.value()
            type ='InternalError'
            errors = ['An exception has occurred' ]
            timestamp = LocalDateTime.now()
        }
        return apiError
    }
}
