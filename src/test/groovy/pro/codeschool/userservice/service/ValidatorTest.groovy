package pro.codeschool.userservice.validator

import org.hibernate.validator.internal.engine.ValidatorImpl
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import pro.codeschool.userservice.api.model.User

import javax.validation.ConstraintViolation
import javax.validation.Validation

@SpringBootTest
class ValidatorTest {

    @Test
    void 'test email validation'() {
        ValidatorImpl validator = Validation.buildDefaultValidatorFactory().validator
        User user = new User(email: 'awesomeyahoo.com.au', firstName: 'Awesome', lastName: 'Me', password: 'awesome')
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user)
        print constraintViolations
        assert constraintViolations.size()
    }

}
