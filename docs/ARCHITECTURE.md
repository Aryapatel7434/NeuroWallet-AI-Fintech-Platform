# NeuroWallet Architecture

## Overview

NeuroWallet is a Spring Boot based fintech backend application designed to manage:

- User Authentication
- Wallet Management
- Transaction Processing
- Scheduled Transactions
- Security Monitoring

---

## Layered Architecture

Client

↓

Controller Layer

↓

Service Layer

↓

Repository Layer

↓

MySQL Database

---

## Controller Layer

Responsibilities:

- Handle HTTP Requests
- Request Validation
- Response Formatting

Examples:

- AuthController
- UserController
- WalletController
- TransactionController

---

## Service Layer

Responsibilities:

- Business Logic
- Security Validation
- Transaction Processing

Examples:

- AuthService
- UserService
- WalletService
- TransactionService

---

## Repository Layer

Responsibilities:

- Database Operations
- CRUD Operations

Examples:

- UserRepository
- WalletRepository
- TransactionRepository

---

## Security Components

- JWT Authentication
- Refresh Tokens
- Password Reset Flow
- Rate Limiting
- Audit Logging

---

## Performance Components

- Redis Caching
- Database Indexing
- Scheduled Processing

---

## Technology Stack

Backend:
- Java 17
- Spring Boot 3

Database:
- MySQL

Security:
- Spring Security
- JWT

Caching:
- Redis

Documentation:
- Swagger OpenAPI