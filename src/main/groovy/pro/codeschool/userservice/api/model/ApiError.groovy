package pro.codeschool.userservice.api.model

import io.swagger.annotations.ApiModel
import org.springframework.beans.factory.annotation.Value

@ApiModel(description = "Error structure to return in case of API errors")
class ApiError {

    int code
    String type

    @Value('${api.name}')
    String system

    List<String> errors
    Long timestamp
}
