# 💳 NeuroWallet-AI-Fintech-Platform

A secure FinTech backend system built using **Java, Spring Boot, MySQL, Spring Data JPA, Hibernate, REST APIs, and JWT Authentication**.

This project is part of my journey to build an **AI-powered Smart Wallet and future Decentralized Payment System**.

---

## 🚀 Project Progress

### ✅ Day 2: REST API Foundation
- Created Spring Boot backend
- Built user registration API
- Integrated MySQL database
- Created wallet automatically after user registration
- Tested APIs using Postman

### ✅ Day 3: Transaction System
- Added money transfer API
- Implemented sender/receiver validation
- Added wallet balance update logic
- Added transaction history table
- Built transaction history API

### ✅ Day 4: JWT Authentication
- Added login API
- Verified user credentials from database
- Generated JWT token after successful login
- Added JWT utility class
- Integrated JWT dependency using Maven

---
# 🔐 Day 5 — JWT Authentication & Protected APIs

## 🚀 Smart Wallet REST API

Day 5 focused on implementing **JWT Authentication** and securing transaction APIs using **Spring Security**.

This transformed the project from a basic REST backend into a **secure FinTech backend system**.

---

# ✅ Features Implemented

## 🔑 JWT Authentication
- User login API
- JWT token generation
- Token validation
- Secure authentication flow

---

## 🛡️ Spring Security Integration
- Added Spring Security dependency
- Configured security filter chain
- Added JWT filter
- Protected transaction APIs

---

## 💸 Protected Transaction APIs
Only authenticated users with valid JWT tokens can:

- Send money
- Access transaction history

---

# 🧠 Authentication Flow

```text
User Login
↓
JWT Token Generated
↓
Token Sent in Authorization Header
↓
JWT Filter Validates Token
↓
Protected API Access Granted
## 🧠 Backend Architecture

```text
Client / Postman
↓
Controller
↓
Service
↓
Repository
↓
MySQL Database


# Day 6 — BCrypt Password Encryption

## Features Implemented
- Added BCrypt password hashing
- Secure password storage
- Password verification using BCrypt
- JWT authentication integration
- Protected APIs with token validation

## Security Improvements
- Plain-text passwords removed
- Encrypted password storage
- Industry-level authentication flow

## APIs Tested
- User Registration
- User Login
- Protected Transaction APIs

## Tech Used
- Spring Boot
- Spring Security BCrypt
- JWT
- MySQL
- JPA/Hibernate
