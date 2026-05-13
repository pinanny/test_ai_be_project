package com.pinanny.testaibeproject.resourcefolder;

import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
class ResourceFolderService {

	private final Clock clock;
	private final Map<UUID, ResourceFolder> folders = new ConcurrentHashMap<>();

	ResourceFolderService() {
		this(Clock.systemUTC());
	}

	ResourceFolderService(Clock clock) {
		this.clock = clock;
	}

	ResourceFolder create(String name) {
		ResourceFolder folder = new ResourceFolder(UUID.randomUUID(), name, Instant.now(this.clock));
		this.folders.put(folder.id(), folder);
		return folder;
	}

	Optional<ResourceFolder> findById(UUID id) {
		return Optional.ofNullable(this.folders.get(id));
	}

}
