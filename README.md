# 🏫 BETA Digital Hub — Frontend

### A modern, full-stack digital platform for BETA (Bench for Electronics and Telecommunication Association), MMCOE Pune

Manage events, team records, alumni, notices, document templates, and admin alerts — all through a fast, responsive, dark/light-mode interface, with a built-in AI assistant answering questions from live organization data.

[![Live App](https://img.shields.io/badge/Live_App-Visit-2563eb?style=for-the-badge)](#)
[![React](https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge&logo=react&logoColor=white)](#)
[![Vite](https://img.shields.io/badge/Vite-Build-646CFF?style=for-the-badge&logo=vite&logoColor=white)](#)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-4-38BDF8?style=for-the-badge&logo=tailwindcss&logoColor=white)](#)

🔗 **Live App:** https://beta-hub.netlify.app
⚙️ **Backend API:** https://beta-hub-be.onrender.com · [Backend Repo](https://github.com/Sujit9911/BETA_HUB_BE)

---

## 📖 Overview

BETA Digital Hub's frontend is the user-facing half of a full-stack organization management platform. It connects to a Spring Boot REST API to deliver a public landing page, a role-aware authenticated dashboard, and six core modules — built for actual departmental use, not just as a demo.

---

## ✨ Features

- 🔐 **JWT authentication** — register/login, persisted session, role-aware UI (Admin/Member)
- 📅 **Events** — category filters, per-event photo/document galleries, coordinator info
- 👥 **Team** — year-wise core committee, auto-created academic year tabs
- 🎓 **Alumni** — searchable directory with batch/domain/company filters
- 📄 **Templates** — reusable document library with file uploads
- 📢 **Notices** — pin/unpin announcements, live dashboard notice board
- 🔔 **Alerts** — admin-broadcast notifications with per-user read tracking and Google Meet links
- 🔍 **Global search** — one search bar across Events, Team, Alumni, Templates, Notices
- ✨ **Ask BETA** — AI assistant (Spring AI + Gemini) answering questions from live app data
- 🌗 **Dark/light theme**, fully responsive, public landing page + protected dashboard

---

## 🛠️ Tech Stack

React 19 · Vite · Tailwind CSS 4 · React Router · Axios · react-markdown

---

## 🚀 Getting Started

```bash
git clone https://github.com/Sujit9911/BETA_HUB_FE.git
cd BETA_HUB_FE
npm install
```

Create `.env`:
```
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

Run:
```bash
npm run dev
```

---

## 🔗 Related

⚙️ **Backend:** [BETA_HUB_BE](https://github.com/Sujit9911/BETA_HUB_BE) — Spring Boot, MySQL, Cloudinary, Spring AI

---

## 👨‍💻 Developer

**Sujit Gawali** — Electronics & Telecommunication Engineering, MMCOE Pune
[GitHub](https://github.com/Sujit9911)
