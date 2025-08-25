# Coffee Machine Adapters Module

This module contains the technology-specific implementations of the ports defined in the application layer. It is the outermost layer of the hexagonal architecture and is responsible for all interactions with the outside world.

## Adapters Overview

The adapters are divided into two main categories: input and output.

*   **Input Adapters**: These adapters are responsible for receiving input from the user and invoking the appropriate use cases in the application layer.
*   **Output Adapters**: These adapters are responsible for persisting the state of the domain model and for sending output to the user.

### Input Adapters

*   **`CliRunner`**: This is a Spring `CommandLineRunner` that provides a command-line interface for interacting with the coffee machine. It parses user input, invokes the appropriate use cases, and prints the results to the console.

### Output Adapters

*   **`CoffeeMachineH2Repository`**: This is an implementation of the `CoffeeMachineRepository` port that uses an in-memory H2 database to persist the state of the `CoffeeMachine` aggregate.
*   **`RecipeH2Repository`**: This is an implementation of the `RecipeRepository` port that uses an in-memory H2 database to persist the `Recipe` aggregate.
*   **`ConsoleDomainEventPublisher`**: This is an implementation of the `DomainEventPublisher` port that simply prints the domain events to the console.

## Technology Stack

*   **Spring Boot**: The application is built on top of the Spring Boot framework.
*   **Spring Data JPA**: The database adapters use Spring Data JPA to interact with the H2 database.
*   **H2**: The application uses an in-memory H2 database for persistence.
*   **Flyway**: Flyway is used to manage the database schema and to seed the database with initial data.
*   **Kotlin Logging**: The application uses Kotlin Logging for logging.