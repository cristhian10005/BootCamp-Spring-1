package com.Bootcamp.Ejercicio1.Spring;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI bookOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Api Laptos")
                        .version("1.0")
                        .description("Example app Spring laptos")
                        .termsOfService("http://swagger.io/terms")
                        .license(new License().name("apache 2.0").url("http://springdoc.org")));
    }
}
