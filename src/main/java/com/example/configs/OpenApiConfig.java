package com.example.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("3Line Coding Exercise API")
                        .description("API for managing customer current accounts, including account opening and transaction management.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("3Line")
                                .email("@growwith3line")
                                .url("https://www.3lineng.com/contact.html")));
    }
}
