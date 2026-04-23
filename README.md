# Mimce AI Workshop

This repository now contains a Spring Boot workshop project at the repository root.

## Clone

```bash
git clone --recurse-submodules https://github.com/muhsin09/mimce-ai-workshop.git
```

Or if cloned without submodules:

```bash
git clone https://github.com/muhsin09/mimce-ai-workshop.git
cd mimce-ai-workshop
git submodule update --init --recursive
```

## Submodule

This repo uses `.opencode` submodule from [addyosmani/agent-skills](https://github.com/addyosmani/agent-skills).

## Project Stack

- Java: **25**
- Spring Boot: **4.0.5**
- Build Tool: **Maven Wrapper** (`mvnw`)

### Main Dependencies

- `spring-boot-starter-web`
- `spring-boot-starter-thymeleaf`
- `spring-boot-starter-security`
- `thymeleaf-extras-springsecurity6`
- `spring-boot-starter-validation`
- `spring-boot-starter-actuator`
- Test: `spring-boot-starter-test`, `spring-security-test`

## Run the Project

From repository root:

```bash
./mvnw spring-boot:run
```

## Build / Test

```bash
./mvnw clean compile
./mvnw test
```

## Application Configuration

Application settings are in:

- `src/main/resources/application.properties`

Current key values:

- `server.port=8080`
- `spring.application.name=mimce-ai-workshop`
- `spring.thymeleaf.cache=false`
- `logging.level.org.springframework.security=INFO`

## Spring Boot Project Setup

If you clone the repository and your IDE does not recognize it as a Maven (`pom.xml`) project immediately, do the following:

1. Make sure Maven wrapper files are present (`mvnw`, `.mvn/`, `pom.xml`).
2. From the project root, run:

```bash
./mvnw clean compile
```

3. In your IDE, re-import/sync the Maven project:
   - IntelliJ IDEA: right-click `pom.xml` -> **Add as Maven Project** (or use the Maven tool window -> Reload All Maven Projects)
   - VS Code: run **Java: Import Java Projects** (or reopen the folder)

## Git Ignore Note

Build outputs are ignored:

- `target/`

## License

MIT
