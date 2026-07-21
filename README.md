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

# 👨‍💻 Author

**Om Gupta**

Backend Developer

Tech Stack:

Java • Spring Boot • Spring Cloud • Microservices • Kafka • Redis • MySQL • Spring Security • Spring AI • Docker • AWS

---

⭐ If you found this repository useful, consider giving it a star.
