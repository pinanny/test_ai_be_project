package com.pinanny.testaibeproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(HelloWorldController.class)
class HelloWorldControllerTests {

	@Autowired
	private MockMvcTester mvc;

	@Test
	void helloReturnsHelloWorld() {
		this.mvc.get().uri("/hello")
				.exchange()
				.assertThat()
				.hasStatusOk()
				.hasBodyTextEqualTo("Hello world");
	}

}
