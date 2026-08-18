<div align="center">

# 🏫 BETA Digital Hub — Backend API

### A secure, full-stack REST API powering BETA's digital organization platform

Built with **Spring Boot 4**, **Java 17**, and **JWT authentication** — powering event management, team & alumni records, admin alerts, document templates, and an AI assistant.

[![Live API](https://img.shields.io/badge/API-Live-brightgreen?style=for-the-badge&logo=render)](https://beta-hub-be.onrender.com/api/v1)
[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-Gemini-blueviolet?style=for-the-badge&logo=google)](https://spring.io/projects/spring-ai)

🔗 **Live API Base URL:** [`https://beta-hub-be.onrender.com/api/v1`](https://beta-hub-be.onrender.com/api/v1)
📦 **Frontend Repo:** [github.com/Sujit9911/BETA_HUB_FE](https://github.com/Sujit9911/BETA_HUB_FE)

</div>

---

## 📖 Overview

BETA Hub's backend is the full data engine behind BETA Digital Hub — the official platform for the Bench for Electronics and Telecommunication Association at MMCOE, Pune. It handles authentication, role-based access, six core modules, cloud media storage, automated email, and an AI assistant, built as a production-oriented full-stack platform.

---

## ✨ Features

🔐 **JWT Authentication** — stateless login & registration, passwords hashed with BCrypt
👮 **Role-Based Access Control** — `ADMIN` / `MEMBER` roles enforced at the endpoint level
📅 **Event Management** — CRUD, category filtering, per-event Cloudinary photo/document uploads
👥 **Team Records** — year-wise core committee, auto-created academic years
🎓 **Alumni Directory** — profile records with batch/domain/company filtering
📄 **Document Templates** — reusable proposal/letter/certificate library with file storage
📢 **Notices** — announcements with pin/unpin
🔔 **Admin Alerts** — broadcast notifications with per-user read tracking and optional Google Meet link
🔍 **Global Search** — cross-module search across Events, Team, Alumni, Notices, and Document Templates
🤖 **Ask BETA** — AI assistant integrated with **Spring AI and Google Gemini**, interacting with application data through backend tools to provide contextual information from the platform
☁️ **Cloudinary Integration** — media and document files stored via Cloudinary, keeping file storage separate from the relational database
📧 **Email Integration** — application-related email delivery via **Brevo API**

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot 4 |
| Security | Spring Security |
| Authentication | JWT |
| Password Hashing | BCrypt |
| Persistence | Spring Data JPA |
| ORM | Hibernate |
| Database | PostgreSQL |
| AI | Spring AI + Google Gemini |
| Media Storage | Cloudinary |
| Email | Brevo API |
| Build Tool | Maven |
| Deployment | Render |
| Frontend | React + Vite |

---


External services:

```text
Spring Boot
   ├── PostgreSQL
   ├── Cloudinary
   ├── Google Gemini
   └── Brevo
```

---


## 🔐 Authentication Flow

**Registration** — `POST /api/v1/auth/register`

The backend:
1. Validates registration data
2. Checks whether the email already exists
3. Hashes the password using BCrypt
4. Creates the user
5. Assigns the default role
6. Generates a JWT
7. Returns authentication information

**Login** — `POST /api/v1/auth/login`

The backend authenticates the credentials and returns a JWT. The frontend then sends the token with protected requests:
```
Authorization: Bearer <token>
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL
- Git

### Clone Repository
```bash
git clone https://github.com/Sujit9911/BETA_HUB_BE.git
cd BETA_HUB_BE
```

### Run Locally

Using Maven Wrapper:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

The backend runs on `http://localhost:8080`, API base path `http://localhost:8080/api/v1`.

---

## ☁️ Deployment

The backend is deployed on **Render**.

- **Production API:** `https://beta-hub-be.onrender.com`
- **Production database:** PostgreSQL


---

## 🔒 Security Highlights

- JWT-based stateless authentication
- BCrypt password hashing
- Role-based authorization
- Protected API endpoints
- Custom JWT authentication filter
- CORS configuration for development and production
- No passwords stored in plain text
- External file storage through Cloudinary
- Secrets managed through environment variables

---


---

## 👨‍💻 Author

**Sujit Gawali**
Java Full Stack Developer

Built as a production-oriented full-stack platform for BETA Digital Hub, covering backend architecture, authentication, database design, cloud storage, AI integration and deployment.

---

## ⭐ Project

If you find this project useful or interesting, consider giving the repository a ⭐.

**Backend:** [github.com/Sujit9911/BETA_HUB_BE](https://github.com/Sujit9911/BETA_HUB_BE)
**Frontend:** [github.com/Sujit9911/BETA_HUB_FE](https://github.com/Sujit9911/BETA_HUB_FE)
