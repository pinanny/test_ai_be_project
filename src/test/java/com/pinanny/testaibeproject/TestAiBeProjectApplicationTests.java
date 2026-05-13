package com.pinanny.testaibeproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class TestAiBeProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void writesHelloWorldToTerminal(CapturedOutput output) throws Exception {
		CommandLineRunner runner = new TestAiBeProjectApplication().helloWorldRunner();

		runner.run();

		assertThat(output).contains("Hello world");
	}

}
