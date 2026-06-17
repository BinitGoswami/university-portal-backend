package com.studentmanagement.university_portal_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class UniversityPortalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityPortalBackendApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void displaySwaggerLink() {
		System.out.println("\n=========================================================");
		System.out.println("SWAGGER UI DOCUMENTATION IS LIVE!");
		System.out.println("Click here: http://localhost:8081/swagger-ui/index.html");
		System.out.println("=========================================================\n");
	}
}