# AI Workshop

A collection of skills for Claude.ai and Claude Code for senior software engineers.

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

## License

MIT
