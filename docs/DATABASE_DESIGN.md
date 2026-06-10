# Database Design

## Overview

NeuroWallet uses MySQL as the primary relational database.

The database layer is designed to support:

- User Management
- Wallet Management
- Fund Transfers
- Scheduled Transactions
- JWT Authentication
- Password Recovery
- Security Auditing

---

# Core Database Tables

## Users Table

Purpose:

Stores registered users and authentication information.

| Column | Type |
|----------|----------|
| user_id | BIGINT |
| name | VARCHAR |
| email | VARCHAR |
| password | VARCHAR |
| role | VARCHAR |

Example Roles:

- USER
- ADMIN

---

## Wallet Table

Purpose:

Stores wallet balance information for users.

| Column | Type |
|----------|----------|
| wallet_id | BIGINT |
| balance | DECIMAL |
| currency | VARCHAR |
| status | VARCHAR |
| created_at | DATETIME |
| updated_at | DATETIME |
| user_id | BIGINT |

Relationship:

User (1) → Wallet (1)

---

## Transaction Table

Purpose:

Stores all wallet transfer history.

| Column | Type |
|----------|----------|
| transaction_id | BIGINT |
| sender_email | VARCHAR |
| receiver_email | VARCHAR |
| amount | DECIMAL |
| status | VARCHAR |
| timestamp | DATETIME |

Transaction Status:

- SUCCESS
- FAILED

---

## Scheduled Transaction Table

Purpose:

Stores future scheduled money transfers.

| Column | Type |
|----------|----------|
| scheduled_transaction_id | BIGINT |
| sender_email | VARCHAR |
| receiver_email | VARCHAR |
| amount | DECIMAL |
| scheduled_time | DATETIME |
| status | VARCHAR |

Status Values:

- PENDING
- SUCCESS
- FAILED

---

## Refresh Token Table

Purpose:

Supports JWT refresh token workflow.

| Column | Type |
|----------|----------|
| id | BIGINT |
| email | VARCHAR |
| token | VARCHAR |
| expiry_date | DATETIME |

Security Benefit:

Allows issuing new access tokens without requiring user login.

---

## Password Reset Token Table

Purpose:

Supports forgot-password functionality.

| Column | Type |
|----------|----------|
| id | BIGINT |
| email | VARCHAR |
| token | VARCHAR |
| expiry_date | DATETIME |

Security Benefit:

Enables secure password recovery.

---

## Audit Log Table

Purpose:

Tracks security and user activity.

| Column | Type |
|----------|----------|
| id | BIGINT |
| action | VARCHAR |
| status | VARCHAR |
| timestamp | DATETIME |
| user_email | VARCHAR |

Tracked Events:

- LOGIN
- LOGIN_FAILED
- LOGIN_BLOCKED
- PASSWORD_RESET
- TOKEN_REFRESH

---

# Entity Relationships

User
│
├── Wallet
│
├── Transaction
│
├── Scheduled Transaction
│
├── Refresh Token
│
├── Password Reset Token
│
└── Audit Log

---

# Security Features

Implemented:

- BCrypt Password Encryption
- JWT Authentication
- Refresh Tokens
- Role Based Access Control
- Audit Logging
- Account Lock Protection
- Password Reset Tokens

---

# Future Enhancements

- Email Notifications
- Transaction Categories
- AI Spending Analytics
- Fraud Detection
- Multi Currency Wallets
- Cloud Database Replication

---

# Database Technology

Database: MySQL

ORM: Spring Data JPA

Connection Pool: HikariCP

Migration Strategy:

Future integration with Flyway or Liquibase.