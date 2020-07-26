package pro.codeschool.userservice.api.model


import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@ToString(includeFields = true, excludes = 'password, id', includePackage = false)
class User {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id

    @NotNull(message = '{invalid.first.name}')
    String firstName

    @NotNull(message = '{invalid.last.name}')
    String lastName

    @Email(message = '{invalid.email.message}')
    String email

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String currentToken
}
