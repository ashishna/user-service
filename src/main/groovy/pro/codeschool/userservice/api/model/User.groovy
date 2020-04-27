package pro.codeschool.userservice.api.model

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class User {

    String id

    @NotNull(message = '{invalid.first.name}')
    String firstName

    @NotNull(message = '{invalid.last.name}')
    String lastName

    @Email(message = '{invalid.email.message}')
    String email

    @NotNull
    @Size(min = 8, max = 15, message = '{invalid.password.length}')
    String password

    @JsonIgnore
    String token
}