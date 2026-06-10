# API Documentation

## Overview

NeuroWallet exposes REST APIs for authentication, wallet management, transaction processing, password recovery, and security operations.

Base URL:

http://localhost:8080

Swagger URL:

http://localhost:8080/swagger-ui/index.html

---

# Authentication APIs

## Login

Endpoint:

POST /api/auth/login

Request:

```json
{
  "email": "admin@gmail.com",
  "password": "Admin@123"
}
```

Response:

```json
{
  "accessToken": "...",
  "refreshToken": "..."
}
```

Purpose:

Authenticate users and generate JWT tokens.

---

## Refresh Token

Endpoint:

POST /api/auth/refresh

Request:

```json
{
  "refreshToken": "token"
}
```

Purpose:

Generate a new access token.

---

## Forgot Password

Endpoint:

POST /api/auth/forgot-password

Request:

```json
{
  "email": "admin@gmail.com"
}
```

Purpose:

Generate password reset token.

---

## Reset Password

Endpoint:

POST /api/auth/reset-password

Request:

```json
{
  "token": "reset-token",
  "newPassword": "Admin@123"
}
```

Purpose:

Update forgotten password.

---

# User APIs

## Register User

Endpoint:

POST /api/users/register

Purpose:

Create a new user account.

---

## Get All Users

Endpoint:

GET /api/users

Authorization:

ROLE_ADMIN

Purpose:

Retrieve all registered users.

---

# Wallet APIs

## Create Wallet

Endpoint:

POST /api/wallet/create

Purpose:

Create a wallet for a user.

---

## Get Wallet

Endpoint:

GET /api/wallet/{userId}

Purpose:

Retrieve wallet information.

---

## Update Wallet Balance

Endpoint:

PUT /api/wallet/update

Purpose:

Update wallet balance after transactions.

---

# Transaction APIs

## Transfer Money

Endpoint:

POST /api/transactions/transfer

Request:

```json
{
  "senderEmail": "admin@gmail.com",
  "receiverEmail": "rahul@gmail.com",
  "amount": 100
}
```

Purpose:

Transfer money between users.

---

## Transaction History

Endpoint:

GET /api/transactions/history

Purpose:

Retrieve transaction records.

---

# Scheduled Transaction APIs

## Schedule Transfer

Endpoint:

POST /api/transactions/schedule

Purpose:

Schedule future transactions.

---

## Process Scheduled Transfers

Executed Automatically By Scheduler.

Purpose:

Execute pending scheduled transactions.

---

# Security APIs

## OTP Verification

Endpoint:

POST /api/otp/generate

Endpoint:

POST /api/otp/verify

Purpose:

Email verification workflow.

---

# Response Codes

| Code | Meaning |
|--------|--------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Internal Server Error |

---

# Authentication Flow

User Login

↓

JWT Generated

↓

Client Stores Token

↓

Protected API Access

↓

JWT Validation

↓

Request Processed

---

# Documentation Tool

Swagger OpenAPI

URL:

http://localhost:8080/swagger-ui/index.html