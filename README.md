# Task Manager API

A learning project to explore Java and Spring Boot fundamentals through building a REST API with full CRUD operations.

## Learning Objectives

This project demonstrates key concepts in Java/Spring Boot development:

- 3-layer architecture (Controller, Service, Repository)
- RESTful API design and implementation
- JPA/Hibernate for database operations
- Bean validation and exception handling
- Unit testing with JUnit 5 and Mockito
- Application profiles and configuration
- Docker for database containerization

## Technology Stack

- **Java 17** - Programming language
- **Spring Boot 3.5.4** - Application framework
- **PostgreSQL 15** - Database
- **Docker Compose** - Database containerization
- **JUnit 5 & Mockito** - Testing framework
- **Maven** - Build tool
- **Lombok** - Code generation

## Quick Setup

1. **Clone and navigate to project**

   ```bash
   git clone <repository-url>
   cd task-manager
   ```

2. **Start PostgreSQL database**

   ```bash
   docker-compose up -d
   ```

3. **Run the application**

   ```bash
   ./mvnw spring-boot:run
   ```

4. **API will be available at:** `http://localhost:8080`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update existing task |
| DELETE | `/api/tasks/{id}` | Delete task |

## Example Usage

**Create a task:**

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot","description":"Complete the task manager tutorial","completed":false}'
```

**Get all tasks:**

```bash
curl http://localhost:8080/api/tasks
```

## Project Structure

```txt
src/main/java/io/github/gabrielfagundeznievas/taskmanager/
├── controller/     # REST endpoints
├── service/        # Business logic
├── repository/     # Data access layer
├── entity/         # JPA entities
└── exception/      # Custom exceptions
```

## Testing

Run unit tests:

```bash
./mvnw test
```

The project includes comprehensive unit tests for the service layer using JUnit 5 and Mockito.

## Key Features Implemented

- Task entity with validation constraints
- Full CRUD REST API
- Custom exception handling (404 errors)
- PostgreSQL integration with Docker
- Application profiles (default and dev)
- Comprehensive unit test coverage
- Clean 3-layer architecture

This project serves as a foundation for understanding Spring Boot development patterns and testing strategies.
