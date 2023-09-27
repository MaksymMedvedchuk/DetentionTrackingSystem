package com.arestmanagement.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
	info = @Info(title = "${openapi.info.title}", version = "${openapi.info.version}", description = "${openapi.info.description}"),
	servers = {@Server(url = "{host}", variables = @ServerVariable(name = "host", defaultValue = "http://localhost:8081"))})
public class SwaggerConfig {


}

