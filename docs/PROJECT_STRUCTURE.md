# Project Structure

## Overview

NeuroWallet follows a layered architecture design pattern.

The project structure is organized to maintain:

- Separation of Concerns
- Scalability
- Maintainability
- Testability

---

# Root Structure

```text
src/main/java/com/smartwallet

├── controller
├── service
├── repository
├── model
├── dto
├── security
├── config
├── scheduler
├── filter
├── exception
└── SmartWalletApplication
```

---

# Controller Layer

Package:

```text
controller
```

Responsibilities:

- Handle HTTP Requests
- Accept API Input
- Return API Responses

Examples:

- AuthController
- UserController
- WalletController
- TransactionController

Flow:

Client

↓

Controller

↓

Service

---

# Service Layer

Package:

```text
service
```

Responsibilities:

- Business Logic
- Validation
- Security Rules
- Transaction Processing

Examples:

- AuthService
- UserService
- WalletService
- TransactionService
- RefreshTokenService
- PasswordResetService
- AuditService

Flow:

Controller

↓

Service

↓

Repository

---

# Repository Layer

Package:

```text
repository
```

Responsibilities:

- Database Access
- CRUD Operations
- Query Execution

Examples:

- UserRepository
- WalletRepository
- TransactionRepository
- AuditRepository
- RefreshTokenRepository

Technology:

Spring Data JPA

---

# Model Layer

Package:

```text
model
```

Responsibilities:

- Entity Definitions
- Database Mapping

Examples:

- User
- Wallet
- Transaction
- AuditLog
- RefreshToken
- PasswordResetToken
- ScheduledTransaction

Annotations:

- @Entity
- @Table
- @Column

---

# DTO Layer

Package:

```text
dto
```

Responsibilities:

- Request Objects
- Response Objects
- API Communication

Examples:

- LoginRequest
- AuthResponse
- ForgotPasswordRequest
- ResetPasswordRequest
- RefreshTokenRequest

Benefits:

- Prevent Direct Entity Exposure
- Better Security
- Better API Design

---

# Security Layer

Package:

```text
security
```

Responsibilities:

- Authentication
- Authorization
- JWT Validation

Components:

- JwtUtil
- JwtFilter
- SecurityConfig
- CustomUserDetailsService
- LoginAttemptService

Implemented Features:

- JWT Authentication
- Refresh Tokens
- Rate Limiting
- Role Based Access Control

---

# Scheduler Layer

Package:

```text
scheduler
```

Responsibilities:

- Background Processing
- Scheduled Transactions

Example:

- ScheduledTransactionProcessor

Purpose:

Execute future-dated transfers automatically.

---

# Filter Layer

Package:

```text
filter
```

Responsibilities:

- Request Logging
- Response Logging
- Request Tracking

Example:

- RequestResponseLoggingFilter

Benefits:

- Debugging
- Monitoring
- Auditing

---

# Configuration Layer

Package:

```text
config
```

Responsibilities:

- Bean Configuration
- External Service Setup

Examples:

- RedisConfig
- SwaggerConfig
- SecurityConfig

---

# Exception Handling

Package:

```text
exception
```

Responsibilities:

- Centralized Error Handling
- Standard Error Responses

Benefits:

- Cleaner Controllers
- Consistent API Responses

---

# Request Flow

Client Request

↓

Controller

↓

Service

↓

Repository

↓

Database

↓

Repository

↓

Service

↓

Controller

↓

Response

---

# Architectural Principles

Implemented Principles:

- Layered Architecture
- Dependency Injection
- Separation Of Concerns
- Stateless Authentication
- Secure Coding Practices
- Reusable Components

---

# Project Quality Attributes

Maintainability:

-> High

Scalability:

-> High

Security:

-> High

Extensibility:

-> High

Readability:

-> High

---

# Summary

NeuroWallet follows modern enterprise backend design principles and is structured for real-world fintech application development.