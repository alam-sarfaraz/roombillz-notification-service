package com.inn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
	    info = @Info(
	        title = "RoomBillz Notification Service",
	        version = "v1",
	        description = "Microservice responsible for sending notifications (email, SMS, or in-app) within the RoomBillz application. It ensures users stay informed about bills, payments, and group activity updates in real time.",
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
	        @Server(
	            url = "http://localhost:8082/api/v1/notification",
	            description = "Local Notification Service Server"
	        ),
	        @Server(
		            url = "https://qa/api.roombillz.com/api/v1/notification",
		            description = "QA Server"
		        ),
	        @Server(
	            url = "https://api.roombillz.com/api/v1/notification",
	            description = "Production Server"
	        )
	    },
	    externalDocs = @ExternalDocumentation(
	        description = "RoomBillz Notification API Documentation",
	        url = "https://roombillz.com/docs/notification"
	    )
	)
public class RoomBillzNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomBillzNotificationServiceApplication.class, args);
		System.out.println("************************************************** RoomBillz Notification Service Started Successfully ************************************************************");
	}
}
