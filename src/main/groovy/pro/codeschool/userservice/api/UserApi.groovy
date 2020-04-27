package pro.codeschool.userservice.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pro.codeschool.userservice.api.model.User
import pro.codeschool.userservice.service.UserService

import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping('/v1/users')
class UserApi {

    @Autowired
    UserService userService

    @Validated
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    User create(@Valid @RequestBody User user) {
        return userService.createUser(user)
    }

    @Validated
    @GetMapping('/{id}')
    ResponseEntity<User> read(@PathVariable('id') @NotNull int id) {
        return ResponseEntity.ok(new User(id: id))
    }
}
