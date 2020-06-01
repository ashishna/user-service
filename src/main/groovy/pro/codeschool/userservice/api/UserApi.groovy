package pro.codeschool.userservice.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.service.UserService

import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping('${api.basePath.users}')
//@CrossOrigin
class UserApi {

    private static final Logger LOG = LoggerFactory.getLogger(UserApi)

    @Autowired
    UserService userService

    @Validated
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    User create(@Valid @RequestBody User user) {
        return userService.createUser(user)
    }

    @Validated
    @GetMapping('/{id}')
    User read(@PathVariable('id') @NotNull long id) {
        return userService.getUser(id)
    }

    @Validated
    @GetMapping
    List<User> readAll() {
        return userService.getAll()
    }

    @GetMapping('/resend-token/{id}')
    void resendToken(@PathVariable('id') @NotNull long id) {
        userService.resendToken(id)
    }

    @GetMapping('/validate/{id}/{token}')
    void validate(@PathVariable('id') @NotNull long id, @PathVariable('token') @NotNull String token) {
        LOG.info("Validating token ${token}")
        userService.validate(id, token)
    }
}
