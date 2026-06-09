# System Architecture

## High Level Overview

NeuroWallet follows a layered architecture pattern.

Client
↓
REST API
↓
Controller Layer
↓
Service Layer
↓
Repository Layer
↓
MySQL Database

---

## Architecture Diagram

Frontend / Swagger

↓

Auth Controller
User Controller
Wallet Controller
Transaction Controller

↓

Auth Service
User Service
Wallet Service
Transaction Service

↓

Repositories

↓

MySQL

---

## Layers

### Controller Layer

Responsibilities:

- Handle HTTP requests
- Validate incoming data
- Return API responses

Examples:

- AuthController
- UserController
- WalletController
- TransactionController

---

### Service Layer

Responsibilities:

- Business Logic
- Security Checks
- Validation Rules

Examples:

- AuthService
- UserService
- WalletService
- TransactionService

---

### Repository Layer

Responsibilities:

- Database Access
- CRUD Operations

Examples:

- UserRepository
- WalletRepository
- TransactionRepository

---

## Security Flow

User Login

↓

JWT Token Generated

↓

Client Stores Token

↓

Protected API Request

↓

JWT Filter Validation

↓

Spring Security Authorization

↓

API Access Granted

---

## Additional Components

### Redis Cache

Used For:

- Fast Data Retrieval
- Performance Optimization

### Rate Limiter

Used For:

- Prevent Brute Force Attacks
- API Abuse Protection

### Audit Logging

Used For:

- Security Monitoring
- User Activity Tracking

### Scheduler

Used For:

- Scheduled Transactions
- Background Processing

### Refresh Tokens

Used For:

- Session Renewal
- Improved Security

---

## Design Principles

- Separation Of Concerns
- Layered Architecture
- Dependency Injection
- Stateless Authentication
- Secure Coding Practices