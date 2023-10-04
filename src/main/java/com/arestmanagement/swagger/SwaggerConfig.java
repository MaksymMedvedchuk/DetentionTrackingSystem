package com.arestmanagement.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
	info = @Info(title = "${openapi.info.title}", version = "${openapi.info.version}", description = "${openapi.info.description}"))
public class SwaggerConfig {
}

