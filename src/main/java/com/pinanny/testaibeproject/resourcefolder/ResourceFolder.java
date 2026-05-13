package com.pinanny.testaibeproject.resourcefolder;

import java.time.Instant;
import java.util.UUID;

record ResourceFolder(UUID id, String name, Instant createdAt) {
}
