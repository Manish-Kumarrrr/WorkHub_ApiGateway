# WorkHub_ApiGateway

WorkHub_ApiGateway is a critical component of the **WorkHub** microservices ecosystem. It serves as the single entry point for client interactions, forwarding requests to the appropriate services and ensuring secure access via JWT-based authorization. 

## Key Responsibilities

- **Routing**: Directs client requests to:
  - **WorkHub_User_Service**: Handles user-related operations.
  - **WorkHub_Task_Service**: Manages tasks and gigs.
- **Authorization**: Secures access to services using JWT.
- **Declarative REST Client**: Leverages Spring Feign Client for seamless communication with microservices.
- **Integration**: Works as a bridge between the **WorkHubUI** and backend services.

## Technology Stack

- **Backend**: Spring Boot
- **Declarative REST Client**: Spring Feign Client
- **Authorization**: JWT-based authentication
- **Gateway**: Spring Cloud Gateway

## Features

- Centralized request routing and load balancing.
- Secure, token-based authentication for microservice communication.
- Simplified and declarative inter-service calls using Feign Client.

## Prerequisites

Ensure the following are in place before running this service:

- Java 19
- Spring Boot
- WorkHub_User_Service and WorkHub_Task_Service running and accessible
- JWT secret key for token validation
## Getting Started

Follow the steps below to set up and run WorkHub locally:

### Step 1: Clone the Repository

```bash
git clone https://github.com/Manish-Kumarrrr/WorkHub_ApiGateway.git
cd WorkHub_ApiGateway
```
### Step 2: Configuration

#### `application.yml` Example:

```yaml
spring:
  application:
    name: WorkHub Api Gateway
server:
  port: 8085

UserService: http://localhost:8081
TaskService: http://localhost:8082
WorkHub_UI: http://localhost:3000

cookie:
  expireTime: 3600
env: dev
secret:
  key: CHEFsEVfypW#7g9^k*Z8$VTaK+HaV^jp
```
## Step 3: Start the application
Run the following commands to start the server:
```bash
./mvnw spring-boot:run
```

This `README.md` provides a high-level overview of the service and its features without delving into specific API endpoint details.

