# Project Overview

This is a Spring Boot web application that mimics some of the core functionalities of Instagram. It's built with Java 21, Spring Boot, and uses MySQL for the database. The frontend is built with Thymeleaf.

The main purpose of this project seems to be for learning and practicing Spring Security. The `README.md` file contains a detailed breakdown of the learning process, from setting up Spring Security to implementing user registration and login.

## Key Technologies

*   **Backend**: Java 21, Spring Boot, Spring Security, Spring Data JPA
*   **Frontend**: Thymeleaf
*   **Database**: MySQL
*   **Build Tool**: Gradle

## Architecture

The application follows a standard Model-View-Controller (MVC) architecture.

*   **Controllers**: Handle incoming HTTP requests and map them to the appropriate services.
*   **Services**: Contain the business logic of the application.
*   **Repositories**: Handle data access using Spring Data JPA.
*   **Entities**: Represent the database tables.
*   **DTOs (Data Transfer Objects)**: Used to transfer data between the controller and service layers.

## Building and Running the Project

### Prerequisites

*   Java 21
*   MySQL database named `instagram`

### Running the Application

1.  **Configure the database**: Open `src/main/resources/application.properties` and update the `spring.datasource.username` and `spring.datasource.password` properties with your MySQL credentials.
2.  **Run the application**: Use the following command to run the application:

    ```bash
    ./gradlew bootRun
    ```

The application will be accessible at `http://localhost:8080`.

## Development Conventions

*   **Code Style**: The code follows standard Java conventions.
*   **Lombok**: The project uses Lombok to reduce boilerplate code.
*   **Testing**: The project includes a basic test setup with JUnit and Spring Security Test. To run the tests, use the following command:

    ```bash
    ./gradlew test
    ```
