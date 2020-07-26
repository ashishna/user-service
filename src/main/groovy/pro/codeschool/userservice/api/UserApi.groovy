package pro.codeschool.userservice.api


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import pro.codeschool.userservice.api.model.ApiResult
import pro.codeschool.userservice.api.model.Password
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.service.UserService
import pro.codeschool.userservice.utils.DateUtils

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
    ResponseEntity<User> create(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user)
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path('/{id}').buildAndExpand(createdUser.id).toUri()
        return ResponseEntity.created(location).body(createdUser)
    }

    @Validated
    @GetMapping('/{id}')
    ResponseEntity<User> read(@PathVariable('id') @NotNull Long id) {
        return ResponseEntity.ok(userService.getUser(id))
    }

    @Validated
    @GetMapping
    ResponseEntity<List<User>> readAll() {
        return ResponseEntity.ok(userService.getAll())
    }

    @GetMapping('/resend-token/{id}')
    void resendToken(@PathVariable('id') @NotNull Long id) {
        userService.resendToken(id)
    }

    @GetMapping('/validate/{id}/{token}')
    void validate(@PathVariable('id') @NotNull Long id, @PathVariable('token') @NotNull String token) {
        LOG.info("Validating token ${token}")
        userService.validate(id, token)
    }

    /**
     * Generate UserRest request and sends an email to initiate password reset.
     * @param password
     * @return
     */
    @PostMapping('/reset-password')
    ApiResult resetPassword(@RequestBody Password password) {
        userService.generateResetPasswordToken(password)
        return new ApiResult(success: true, timestamp: DateUtils.now())
    }

    /**
     * Modifies the user password and set's to the provided one
     * @param user
     * @param id
     * @return
     */
    @PatchMapping('/reset-password/{id}')
    ApiResult reset(@RequestBody User user, @PathVariable('id') Long id) {
        userService.resetPassword(user, id)
        return new ApiResult(success: true, timestamp: DateUtils.now())
    }
}
