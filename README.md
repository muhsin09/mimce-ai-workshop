## CI Pipeline

The repository includes a GitHub Actions workflow that builds the project, runs the full test suite, performs an OWASP Dependency‑Check scan, and (optionally) runs Spotless/Checkstyle.

![CI Status](https://github.com/muhsin09/mimce-ai-workshop/actions/workflows/ci.yml/badge.svg)

## Commit Guidelines

Commits must follow the Conventional Commit format (e.g., `feat: add login endpoint`). The repository ships a Husky `commit‑msg` hook that enforces this.

## OpenAPI Documentation

Springdoc‑OpenAPI is integrated. After running the application, visit:

```
http://localhost:8080/swagger-ui.html
```

to explore the generated API specification for the TODO endpoints.

## Security Note

Passwords are now stored using BCrypt hashing. The default user is `mimce` with password `mimce`. Do not use `{noop}` passwords in production.
