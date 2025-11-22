package com.example.pickyback.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;


/**
 *  Swagger 주소 : http://localhost:8080/swagger-ui/index.html#/
 */

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {

		return new OpenAPI();
	}
}