# My Application

This project is a Spring Boot application following Clean Architecture principles, packaged and run using Docker. 
Below is documentation covering build instructions, API access, metrics/health endpoints, and a short explanation of the architectural approach.

---

## 🚀 Running the Application with Docker

Build the Docker image:

```sh
docker build -t my-application:latest .
```

Run the container:

```sh
docker run -p 8080:8080 my-application:latest
```

The application will be available at:

**[http://localhost:8080](http://localhost:8080)**

---

## 📘 OpenAPI / Swagger UI

Once the application is running, the auto-generated API documentation can be accessed at:

**[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)**

This provides an interactive interface to explore and test all available API endpoints.

---

## 🩺 Health & Metrics Endpoint

Spring Boot Actuator provides operational endpoints including health checks and metrics.
Access the health endpoint at:

**[http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)**

Other actuator endpoints (if enabled) may include:

* `/actuator/metrics`
* `/actuator/info`
* `/actuator/prometheus`

---

## 🧱 Clean Architecture — Short Explanation

This project follows **Clean Architecture**, a software design approach that emphasizes separation of concerns and long-term maintainability.

### Core ideas:

### **1. Dependency Rule**

Inner layers must not depend on outer layers. Business logic stays independent of frameworks, databases, and UI.

### **2. Layers Overview**

* **Domain (Entities):** Business rules and core models.
* **Use Cases (Application Layer):** Application-specific business logic; orchestrates domain operations.
* **Adapters / Interfaces:** Controllers, presenters, mappers, database gateways.
* **Infrastructure:** Frameworks, data access, configuration, external services.

### **3. Benefits**

* High testability (use cases can be tested without frameworks).
* Easy to swap technologies (e.g., replace HTTP controller or database layer).
* Clear boundaries reduce coupling and increase maintainability.

---

## 📂 Project Overview

* **Spring Boot** for application runtime and dependency injection.
* **Spring HATEOAS** for rich REST responses.
* **SpringDoc / Swagger** for API documentation.
* **Docker** for containerization and deployment.
* **Clean Architecture** for a clean, scalable, and maintainable codebase.

