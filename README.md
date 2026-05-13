# test-ai-be-project

A small Spring Boot reference service that manages **resource folders** via HTTP. Folder data lives only in memory for the lifetime of the JVM process (no database).

## Tech stack

| Area | Choice |
|------|--------|
| Language | Java 21 |
| Framework | Spring Boot 4.0.x (parent BOM) |
| Build | Maven (`pom.xml`) |
| Packaging | Single JAR, standard `src/main/java` layout |

Main entry point: `com.pinanny.testaibeproject.TestAiBeProjectApplication`.

## Architecture (high level)

- **Web layer** — `ResourceFolderController` (`@RestController`) under base path `/resource-folders`. Uses Spring MVC-style annotations (`@GetMapping`, `@PostMapping`, `@RequestBody`, `@PathVariable`).
- **Domain** — `ResourceFolder` is an immutable `record`: `id` (`UUID`), `name` (`String`), `createdAt` (`Instant`).
- **Application service** — `ResourceFolderService` orchestrates creation and lookup. It uses `java.time.Clock` (default `Clock.systemUTC()`) so time can be overridden in tests.
- **Persistence** — Folders are stored in a `ConcurrentHashMap<UUID, ResourceFolder>`. There is no schema, migrations, or external store.

JSON request/response DTOs: `CreateResourceFolderRequest`, `ResourceFolderResponse`.

## HTTP API (summary)

| Method | Path | Purpose |
|--------|------|---------|
| `POST` | `/resource-folders` | Create a folder; JSON body must include a non-blank `name`. Responds with `201 Created`, `Location: /resource-folders/{id}`, and the created resource in the body. |
| `GET` | `/resource-folders/{id}` | Return a folder by UUID, or `404` if missing. |

Invalid create payloads (null/blank name) yield `400 Bad Request`.

## Run and test

Build and run tests:

```bash
./mvnw test
```

Run the application (after `./mvnw package` if you prefer a packaged JAR):

```bash
./mvnw spring-boot:run
```

The application logs a simple “Hello world” line on startup via a `CommandLineRunner` bean.

## Tests

Automated tests live under `src/test/java`, including `ResourceFolderControllerTests` for the REST layer and `TestAiBeProjectApplicationTests` for context loading.
