package com.pinanny.testaibeproject.resourcefolder;

import java.time.Instant;
import java.util.UUID;

public record ResourceFolderResponse(UUID id, String name, Instant createdAt) {

	static ResourceFolderResponse from(ResourceFolder folder) {
		return new ResourceFolderResponse(folder.id(), folder.name(), folder.createdAt());
	}

}
