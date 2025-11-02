package com.inn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
	    info = @Info(
	        title = "RoomBillz Notification Service",
	        version = "v1",
	        description = "Welcome to the RoomBillz Notification Service — a microservice responsible for managing and sending notifications (email, SMS, or in-app) across the RoomBillz platform. It ensures users stay updated with billing, payments, and group activity in real-time.",
	        termsOfService = "https://roombillz.com/terms",
	        contact = @Contact(
	            name = "Sarfaraz Alam",
	            email = "sarfarazalam2702@gmail.com",
	            url = "https://www.linkedin.com/in/alam-sarfaraz/"
	        ),
	        license = @License(
	            name = "Apache 2.0",
	            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
	        )
	    ),
	    servers = {
	        @Server(url = "http://localhost:8082", description = "Local Development Server"),
	        @Server(url = "https://api.roombillz.com", description = "Production Server")
	    },
	    externalDocs = @ExternalDocumentation(
	        description = "RoomBillz Notifiation service API Documentation and Developer Guide",
	        url = "https://roombillz.com/docs"
	    )
	)
public class RoomBillzNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomBillzNotificationServiceApplication.class, args);
		System.out.println("************************************************** RoomBillz Notification Service Started Successfully ************************************************************");
	}
}
