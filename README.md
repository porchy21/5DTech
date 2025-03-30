# Project README
Overview
This project consists of two microservices (user-service and company-service) that interact with a PostgreSQL database. The services are containerized using Docker and can be easily deployed using Docker Compose.

## Prerequisites
- Docker
- Docker Compose

## Getting Started
To set up and run the application, follow these steps:

### Clone the repository:
```
git clone <repository-url>
cd <repository-directory>
```
### Build and start the services:

```
docker-compose up --build
```

This command will build the Docker images for the services and start the containers.

## Access the services:

- User Service: http://localhost:8080/swagger-ui/index.html#/
- Company Service: http://localhost:8082/swagger-ui/index.html#/

Testing with Swagger
Once the services are up and running, you can test the APIs using Swagger UI:

Open your web browser and navigate to the provided URLs for each service.
Use the Swagger interface to explore and test the available endpoints.

## Stopping the Services
To stop the running services, use the following command:
```
docker-compose down
```
