package com.pinanny.testaibeproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloWorldController {

	@GetMapping("/hello")
	String hello() {
		return "Hello world";
	}

}
