# AGENTS.md

## Cursor Cloud specific instructions

### Project overview

This is a Spring Boot 4.0.6 backend project (`test-ai-be-project`) using Maven and Java 26. The project code currently lives on the `cursor/be-task01-project-structure` branch (the `main` branch contains only the initial `.gitignore`).

### JDK requirement

The project targets **Java 26** (`<java.version>26</java.version>` in `pom.xml`). The VM update script installs Eclipse Temurin JDK 26 automatically. `JAVA_HOME` is set in `~/.bashrc` to `/usr/lib/jvm/jdk-26.0.1+8`.

### Build and test commands

- **Build:** `./mvnw clean package`
- **Test:** `./mvnw clean test`
- **Run:** `./mvnw spring-boot:run`

All commands require `JAVA_HOME` pointing to JDK 26. If the shell does not have it set, run `export JAVA_HOME=/usr/lib/jvm/jdk-26.0.1+8` first (this should already be sourced from `~/.bashrc`).

### Caveats

- The project currently only includes `spring-boot-starter` (no web starter), so `spring-boot:run` starts the application and then exits immediately. This is expected behavior — there is no embedded web server yet.
- No external services (database, Redis, message broker, Docker) are required.
- The Maven Wrapper (`./mvnw`) is bundled in the repo; no global Maven installation is needed.
- Mockito emits dynamic agent warnings on JDK 26 during tests. These are informational and do not affect test results.
