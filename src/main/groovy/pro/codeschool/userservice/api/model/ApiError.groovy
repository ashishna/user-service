package pro.codeschool.userservice.api.model

import org.springframework.beans.factory.annotation.Value

class ApiError {

    int code
    String type

    @Value('${api.name}')
    String system

    List<String> errors
    Long timestamp
}
