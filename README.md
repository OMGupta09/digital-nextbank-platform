# 🏦 DigitalBank NextBank Platform

A production-inspired **microservices-based digital banking platform** built using Spring Boot and Spring Cloud.

The goal of this project is to simulate how modern banking systems are designed in the industry by separating business capabilities into independently deployable services, secured through JWT authentication, connected through service discovery, and integrated using synchronous (Feign) and asynchronous (Kafka) communication.

---

## ✨ Features

- Microservices Architecture
- API Gateway
- Eureka Service Discovery
- JWT Authentication
- Refresh Token Authentication
- Role Based Authorization
- Customer Management
- Bank Account Management
- Money Transfer
- Deposit & Withdrawal
- Kafka Event Streaming
- Redis Caching
- OpenFeign Service Communication
- AI Banking Assistant using Spring AI
- Global Exception Handling
- Validation
- Centralized Request Logging
- Correlation ID Tracking

---

# 🏗 Architecture

```
                    Client
                       │
                       ▼
                API Gateway
                       │
          JWT Authentication Filter
                       │
     ┌─────────────────┼──────────────────┐
     │                 │                  │
     ▼                 ▼                  ▼
 Auth Service     Customer Service    AI Service
     │                 │                  │
     ▼                 ▼                  │
 Account Service ◄─────┘                  │
     │                                    │
     ▼                                    │
Transaction Service ───────────────► Kafka
     │                                    │
     ▼                                    ▼
 MySQL / Redis                 Notification Service

                Eureka Discovery Server
```

---

# 📦 Services

| Service | Responsibility |
|----------|----------------|
| API Gateway | Entry point, JWT validation, routing |
| Discovery Server | Service registration & discovery |
| Auth Service | Authentication, JWT, Refresh Tokens |
| Customer Service | Customer profiles & KYC |
| Account Service | Bank account management |
| Transaction Service | Deposit, Withdraw & Transfers |
| Notification Service | Kafka based notifications |
| AI Service | AI-powered banking assistant |

---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot 3
- Spring Cloud
- Spring Security
- Spring Data JPA
- Spring Validation
- Spring AI

## Databases

- MySQL
- Redis

## Messaging

- Apache Kafka

## Cloud

- Eureka Discovery
- Spring Cloud Gateway
- OpenFeign

## Build

- Maven

---

# 🔄 Service Communication

### Synchronous

- OpenFeign Clients

Used between:

- Auth → Customer
- Account → Customer
- Transaction → Customer
- Transaction → Account
- AI → Customer
- AI → Account
- AI → Transaction

---

### Asynchronous

Apache Kafka

Transaction Service publishes:

- TransactionCompletedEvent
- TransactionFailedEvent

Notification Service consumes these events.

---

# 🔐 Authentication Flow

```
Register
      │
      ▼
Create User
      │
      ▼
Create Customer Profile
      │
      ▼
Login
      │
      ▼
Generate JWT
      │
      ▼
API Gateway validates JWT
      │
      ▼
Forward request to services
```

---

# 📁 Repository Structure

```
digital-nextbank-platform

├── infrastructure
│   ├── api-gateway
│   └── discovery-server
│
└── services
    ├── auth-service
    ├── customer-service
    ├── account-service
    ├── transaction-service
    ├── notification-service
    └── ai-service
```

---

# 🚀 Current Capabilities

✅ User Registration

✅ Login

✅ Refresh Tokens

✅ Customer Creation

✅ Customer KYC

✅ Account Creation

✅ Deposit

✅ Withdraw

✅ Transfer

✅ Kafka Event Publishing

✅ Kafka Event Consumption

✅ Redis Integration

✅ AI Banking Assistant

---

# 🔮 Planned Enhancements

- Docker Compose
- Kubernetes Deployment
- Distributed Tracing
- Prometheus & Grafana
- Resilience4J Circuit Breakers
- Rate Limiting
- Audit Logging
- Email Notifications
- SMS Notifications
- CI/CD Pipeline
- Unit & Integration Tests

---

# 🎯 Learning Objectives

This project demonstrates practical implementation of:

- Microservices Architecture
- Domain Driven Service Separation
- Secure Authentication
- REST API Design
- Event Driven Architecture
- Distributed Systems
- Service Discovery
- API Gateway
- AI Integration in Enterprise Applications

---

# 🚀 Getting Started

## Prerequisites

Make sure you have the following installed:

- Java 21
- Maven 3.9+
- Docker & Docker Compose
- Git
- MySQL (or Docker)
- Redis
- Apache Kafka

---

## Clone the Repository

```bash
git clone https://github.com/OMGupta09/digital-nextbank-platform.git
```

Move into the project directory:

```bash
cd digital-nextbank-platform
```

---

## Build the Project

From the root directory, build all services:

```bash
mvn clean install
```

Or build individual services:

```bash
cd services/auth-service
mvn clean install
```

---

## Start Infrastructure

If you're using Docker:

```bash
docker compose up -d
```

This starts:

- MySQL
- Redis
- Kafka
- Zookeeper

---

## Start the Services

Start the services in the following order:

1. Discovery Server

```bash
cd infrastructure/discovery-server
mvn spring-boot:run
```

2. API Gateway

```bash
cd infrastructure/api-gateway
mvn spring-boot:run
```

3. Auth Service

```bash
cd services/auth-service
mvn spring-boot:run
```

4. Customer Service

```bash
cd services/customer-service
mvn spring-boot:run
```

5. Account Service

```bash
cd services/account-service
mvn spring-boot:run
```

6. Transaction Service

```bash
cd services/transaction-service
mvn spring-boot:run
```

7. Notification Service

```bash
cd services/notification-service
mvn spring-boot:run
```

8. AI Service

```bash
cd services/ai-service
mvn spring-boot:run
```

---

## Verify the Setup

Open Eureka Dashboard:

```
http://localhost:8761
```

Verify that all services are registered before making API requests.


⭐ If you found this repository useful, consider giving it a star.
