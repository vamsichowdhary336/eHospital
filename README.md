# 🏥 Hospital Management System

A full-stack **Hospital Management System** built using **Spring Boot** and **HTML, CSS, JavaScript**, enabling secure appointment management, role-based access, and digital medical reports with a clean REST-based architecture.

---
## 📌 Project Description

Hospital Management System is designed to simulate a **real-world hospital workflow** using modern backend architecture.

It allows **patients** to book and manage appointments, **doctors** to approve or deny appointments and upload reports, and **admins** to manage the system efficiently.

The project focuses on **session-based authentication**, **role-based authorization**, **clean REST APIs**, and **modular backend design** using Spring Boot.

---
## 🚀 Features

### 🔐 Authentication & Authorization
- Session-based login (HttpSession)
- Role-based access: **Doctor / Patient**
- Account lock after multiple failed login attempts

### 🧑‍🦱 Patient Features
- Book new appointments
- Reschedule appointments (date & time)
- Cancel appointments
- View & download medical reports

### 👨‍⚕️ Doctor Features
- View assigned appointments
- Approve or deny appointments
- Upload patient reports (PDF)
- View uploaded reports
### 📧 Email Notifications
- Automatic email on appointment approval/denial
- Gmail SMTP integration

---
## 🛠️ Tech Stack

**Backend**
- Java 25
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- MySQL 

**Frontend**
- HTML5
- CSS3
- JavaScript (Fetch API)

---
## 🌐 Sample API Endpoints

- `/api/auth/login`
- `/api/auth/me`
- `/api/doctor/appointments`
- `/api/doctor/deny/{id}`
- `/api/patient/reports`
- `/api/patient/reports/download/{id}`

---
## 🏠 Homepage

