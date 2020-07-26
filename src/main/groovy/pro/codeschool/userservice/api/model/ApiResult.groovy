package pro.codeschool.userservice.api.model

import io.swagger.annotations.ApiModel


@ApiModel(description = "Api response to return in case of API operations")
class ApiResult {
    boolean success
    Long statusCode
    Long timestamp
}
