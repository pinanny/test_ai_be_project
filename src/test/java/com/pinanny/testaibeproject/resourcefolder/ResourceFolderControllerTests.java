package com.pinanny.testaibeproject.resourcefolder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class ResourceFolderControllerTests {

	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		ResourceFolderService service = new ResourceFolderService();
		this.mvc = standaloneSetup(new ResourceFolderController(service)).build();
	}

	@Test
	void createsResourceFolder() throws Exception {
		this.mvc.perform(post("/resource-folders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"name":" Product Docs "}
						"""))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern("/resource-folders/.+")))
			.andExpect(jsonPath("$.name").value("Product Docs"))
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andExpect(jsonPath("$.createdAt").isNotEmpty());
	}

	@Test
	void returnsCreatedResourceFolderById() throws Exception {
		String location = this.mvc.perform(post("/resource-folders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"name":"Engineering"}
						"""))
			.andExpect(status().isCreated())
			.andReturn()
			.getResponse()
			.getHeader("Location");

		assertNotNull(location);
		String id = location.substring(location.lastIndexOf('/') + 1);

		this.mvc.perform(get(location))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(id))
			.andExpect(jsonPath("$.name").value("Engineering"));
	}

	@Test
	void rejectsBlankResourceFolderName() throws Exception {
		this.mvc.perform(post("/resource-folders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"name":" "}
						"""))
			.andExpect(status().isBadRequest());
	}

	@Test
	void returnsNotFoundForUnknownResourceFolder() throws Exception {
		this.mvc.perform(get("/resource-folders/{id}", UUID.randomUUID()))
			.andExpect(status().isNotFound());
	}

}
