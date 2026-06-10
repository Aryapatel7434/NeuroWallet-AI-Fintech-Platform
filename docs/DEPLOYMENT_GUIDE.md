# Deployment Guide

## Overview

This document explains how to set up, build, and run the NeuroWallet AI Fintech Platform.

The application is containerized using Docker and uses MySQL and Redis as supporting services.

---

# System Requirements

## Software

Required:

- Java 17
- Maven 3.9+
- Docker
- Docker Compose
- Git

Recommended IDE:

- IntelliJ IDEA
- Spring Tool Suite
- VS Code

---

# Clone Repository

```bash
git clone https://github.com/<your-username>/NeuroWallet-AI-Fintech-Platform.git

cd NeuroWallet-AI-Fintech-Platform
```

---

# Database Configuration

MySQL Database:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartwalletdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

---

# Redis Configuration

Redis Default Port:

```text
6379
```

Verify Redis:

```bash
redis-cli ping
```

Expected:

```text
PONG
```

---

# Build Application

```bash
./mvnw clean package -DskipTests
```

Expected:

```text
BUILD SUCCESS
```

---

# Run Application

Using Maven:

```bash
./mvnw spring-boot:run
```

Application URL:

```text
http://localhost:8080
```

---

# Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

API Documentation:

```text
http://localhost:8080/v3/api-docs
```

---

# Docker Deployment

Build Containers:

```bash
docker-compose build
```

Start Containers:

```bash
docker-compose up -d
```

Verify Running Containers:

```bash
docker ps
```

Expected Services:

- neurowallet-app
- mysql
- redis

---

# Container Logs

Application Logs:

```bash
docker logs -f neurowallet-app
```

MySQL Logs:

```bash
docker logs -f mysql
```

Redis Logs:

```bash
docker logs -f redis
```

---

# Security Verification

Verify:

- Login API
- Refresh Token API
- Password Reset API
- JWT Authorization
- Role Based Access Control

Swagger can be used for testing.

---

# Production Deployment Recommendations

Recommended Improvements:

- HTTPS
- Reverse Proxy (Nginx)
- Cloud Database
- Flyway Migrations
- Monitoring & Alerts
- Centralized Logging

---

# Health Check

Application:

```text
http://localhost:8080/actuator/health
```

Expected:

```json
{
  "status": "UP"
}
```

---

# Troubleshooting

## Port Already In Use

```bash
netstat -ano | findstr 8080
```

Kill Process:

```bash
taskkill /PID <pid> /F
```

---

## Docker Container Not Starting

Check:

```bash
docker ps -a
```

View Logs:

```bash
docker logs <container-name>
```

---

## Database Connection Error

Verify:

- MySQL Running
- Credentials Correct
- Database Exists

Database Name:

```text
smartwalletdb
```

---

# Deployment Status

Deployment Environment:

- Local Development 
- Docker Deployment 

Future:

- AWS Deployment
- Kubernetes Deployment
- CI/CD Pipeline