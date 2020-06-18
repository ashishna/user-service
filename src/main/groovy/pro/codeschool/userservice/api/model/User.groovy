package pro.codeschool.userservice.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.ToString

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ToString(includeFields = true, excludes = 'password, id', includePackage = false)
class User {

    @JsonIgnore
    long id

    @NotNull(message = '{invalid.first.name}')
    String firstName

    @NotNull(message = '{invalid.last.name}')
    String lastName

    @Email(message = '{invalid.email.message}')
    String email

    @NotNull
    String password

    String currentToken
}
