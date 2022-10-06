package com.telegrambot.jd501;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class PetShelterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetShelterApplication.class, args);
	}

}
