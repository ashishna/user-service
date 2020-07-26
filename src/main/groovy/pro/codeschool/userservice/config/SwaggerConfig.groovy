package pro.codeschool.userservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Value('${api.name}')
    String apiTitle

    @Bean
    Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage('pro.codeschool.userservice.api'))
                .paths(PathSelectors.regex('/api.*'))
                .build()
                .ignoredParameterTypes(MetaClass)
                .apiInfo(apiInfo(apiTitle))
    }
    //http://localhost:8080/swagger-ui.html
    private static ApiInfo apiInfo(String title) {
        return new ApiInfo(
                title,
                'User registration API',
                "1.0",
                "Terms of service",
                new Contact('Code School', 'http://www.codeschool.pro', 'ashishna@gmail.com'),
                "License of API", "API license URL", [])
    }
}
