package com.pinanny.testaibeproject.resourcefolder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ResourceFolderController.class)
@Import(ResourceFolderService.class)
class ResourceFolderControllerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

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
		String response = this.mvc.perform(post("/resource-folders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"name":"Engineering"}
						"""))
			.andExpect(status().isCreated())
			.andReturn()
			.getResponse()
			.getContentAsString();
		Map<String, Object> created = this.objectMapper.readValue(response, new TypeReference<>() {
		});

		String id = (String) created.get("id");

		this.mvc.perform(get("/resource-folders/{id}", id))
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
