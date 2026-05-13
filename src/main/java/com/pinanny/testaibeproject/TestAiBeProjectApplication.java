package com.pinanny.testaibeproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestAiBeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestAiBeProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner helloWorldRunner() {
		return args -> System.out.println("Hello world");
	}

}
