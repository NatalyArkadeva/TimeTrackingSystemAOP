package com.nataly.timetrackingsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class TimetrackingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimetrackingsystemApplication.class, args);
	}

}
