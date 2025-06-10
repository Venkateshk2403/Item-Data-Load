package com.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


/**
 * Entry point for the Item Data Load Project Spring Boot application.
 * 
 * This class bootstraps the application using Spring Boot's auto-configuration
 * and component scanning features. It initializes the Spring context and starts
 * the embedded server.
 * 
 * Usage:
 * Run this class to start the application.
 */
@OpenAPIDefinition(
	    info = @Info(
	        title = "Item Data Load Application",
	        version = "1.0",
	        description = "API documentation for my secured Spring Boot application"
	    ),
	    security = @SecurityRequirement(name = "BearerAuth") // Apply BearerAuth globally
	)
	@SecurityScheme(
	    name = "BearerAuth", // Name of the security scheme
	    type = SecuritySchemeType.HTTP,
	    scheme = "bearer",
	    bearerFormat = "JWT", // Optional, for documentation purposes
	    description = "JWT authentication using a Bearer token"
	)
@SpringBootApplication
public class ItemDataLoadProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemDataLoadProjectApplication.class, args);
	}

}
