# Spring Boot Microservice EKS Demo App

This project showcases a microservice architecture comprising `user-service`, `employee-service`, and an `api-gateway` for interaction between services. This demo app is designed for deployment on AWS EKS and serves as a learning tool for containerized microservice deployment.

## Features

- **Microservice architecture with Spring Boot**
- **REST APIs with Swagger documentation**
- **In-memory H2 databases for development and testing**
- **Containerized deployment using Docker and Docker Compose**

## Architecture Overview

The application consists of:
- **User Service**: Handles user-related operations.
- **Employee Service**: Manages employee data.
- **API Gateway**: Serves as a unified entry point to access the microservices.

## Screenshots

### 1. API Gateway Swagger UI

![API Gateway Swagger UI](https://github.com/user-attachments/assets/2fa6874d-b02d-4ad8-a716-343e25fcbb33)

The API Gateway exposes the combined Swagger UI for easy interaction with the microservices. Access it at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

### 2. User Service Swagger UI

![User Service Swagger UI](https://github.com/user-attachments/assets/fd58ebf3-6a6a-4984-95c7-47262778fb19)

### 3. Employee Service Swagger UI

![Employee Service Swagger UI](https://github.com/user-attachments/assets/45141a62-6457-44e3-808f-db80b916d015)

## H2 Database Console

### 1. User Database (H2 Console)

![User Database](https://github.com/user-attachments/assets/63b65e4f-ed4c-46f4-a534-4520310fd512)

### 2. Employee Database (H2 Console)

![Employee Database](https://github.com/user-attachments/assets/76509411-7964-49d6-a12b-a1e538a2913c)

Each service includes an H2 database console:
- **User Service H2 Console**: [http://localhost:8081/h2-console](http://localhost:8081/h2-console)
- **Employee Service H2 Console**: [http://localhost:8082/h2-console](http://localhost:8082/h2-console)

Use the JDBC URL, username, and password specified in the `application.yaml` files of the respective services to log in.

## How to Run the Application Using Docker Compose

### Prerequisites

- Docker
- Docker Compose

### Build and Run with Docker Compose

1. Clone the repository to your local machine.
2. Navigate to the root directory where `docker-compose.yml` is located and run:
3. Run the following command to build and start the services:


```bash
docker-compose up --build
```

### Accessing the Services

- API Gateway Swagger UI: http://localhost:8080/swagger-ui/index.html
- User Service H2 Console: http://localhost:8081/h2-console
- Employee Service H2 Console: http://localhost:8082/h2-console

### Default Credentials for H2 Console

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

### Contact

For questions or support, please contact support@samyaktechlabs.com.
