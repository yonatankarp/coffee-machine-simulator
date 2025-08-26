# Coffee Machine Simulator ☕

A Kotlin + Spring Boot hexagonal-architecture playground that simulates an espresso machine. It ships with a CLI, an
in-memory H2 database, and Flyway migrations for schema + seed data.

---

## Disclaimer

This project is created purely for educational purposes.
It is **not a production-ready application** and should not be used as such.
The goal is to explore how Kotlin, Spring Boot, Hexagonal Architecture,
and Domain-Driven Design (DDD) principles can work together in practice.

A few important notes:

- **Pragmatism vs. purity**: In real projects, I’d often lean on Spring annotations in the application layer for
  simplicity. Here, I intentionally avoided them to keep the architectural layers cleanly separated. The same applies to
  the idea of a dedicated `bootstrap` module: while more “pure,” it would force integration tests into a separate module.
  For practicality, I kept them in the `adapters` module.
- **Front-end**: I’m not a front-end developer. Any UI work included (or added in the future) is assisted by AI.
- **Coffee expertise**: I’m no barista! If you notice inaccuracies in the recipes or brewing process, I’d love your feedback.

With that in mind, enjoy!

---

## Getting Started

### Prerequisites

- Java 21
- Gradle 8.x

### Running the CLI

To run the command-line interface:

```bash
./gradlew :coffee-machine-adapters:bootRun --args='--spring.profiles.active=cli'
```

You should see the following prompt:

```bash
INFO  Coffee Machine CLI ☕️
INFO  Commands:
  help                          - show this help
  quit                          - exit
  power on|off                  - turn machine on/off
  status                        - show machine status
  recipes                       - list available recipes
  brew <RECIPE_NAME>            - brew a recipe (e.g., ESPRESSO)
  refill water [ml]             - refill water tank (full if ml omitted)
  refill beans [g]              - refill bean hopper (full if g omitted)
  refill waste                  - empty waste bin
INFO  > 
```

---

## Architecture & Modules

This project follows a clean, hexagonal architecture. For detailed information, please see the `README.md` file in each
module:

- **[`coffee-machine-domain/`](coffee-machine-domain)**: Contains the core domain logic, entities, and value objects.
- **[`coffee-machine-application/`](coffee-machine-application)**: Orchestrates the domain logic to fulfill application
  use cases.
- **[`coffee-machine-adapters/`](coffee-machine-adapters)**: Provides the technology-specific implementations for
  input (CLI) and output (H2 database).

---

## H2 Console

If you run the application with the default profile (without `--spring.profiles.active=cli`), the H2 database console
will be available at:

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:coffee-db`
- **Username**: `sa`
- **Password**: (empty)

---

## Contributing

PRs are welcome! Please see the [Contributing Guide](.github/contributing.md) for details on how to get started.
