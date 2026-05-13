package com.pinanny.testaibeproject.resourcefolder;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/resource-folders")
class ResourceFolderController {

	private final ResourceFolderService resourceFolderService;

	ResourceFolderController(ResourceFolderService resourceFolderService) {
		this.resourceFolderService = resourceFolderService;
	}

	@PostMapping
	ResponseEntity<ResourceFolderResponse> create(@RequestBody CreateResourceFolderRequest request) {
		String name = validateName(request);
		ResourceFolder folder = this.resourceFolderService.create(name);

		return ResponseEntity.created(URI.create("/resource-folders/" + folder.id()))
			.body(ResourceFolderResponse.from(folder));
	}

	@GetMapping("/{id}")
	ResourceFolderResponse get(@PathVariable UUID id) {
		return this.resourceFolderService.findById(id)
			.map(ResourceFolderResponse::from)
			.orElseThrow(() -> new ResourceFolderNotFoundException(id));
	}

	private static String validateName(CreateResourceFolderRequest request) {
		if (request == null || request.name() == null || request.name().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resource folder name is required");
		}
		return request.name().trim();
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	private static final class ResourceFolderNotFoundException extends RuntimeException {

		private ResourceFolderNotFoundException(UUID id) {
			super("Resource folder not found: " + id);
		}

	}

}
