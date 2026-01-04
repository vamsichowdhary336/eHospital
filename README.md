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
## 📸 Screenshots
## 🏠 Homepage
<img width="1441" height="845" alt="Screenshot 2026-01-04 122617" src="https://github.com/user-attachments/assets/c9be655b-17dc-44ce-950f-81de1765013c" /> <br>
## 🔐 Login Page
<img width="1213" height="843" alt="Screenshot 2026-01-04 124614" src="https://github.com/user-attachments/assets/a1f89dd4-4721-4c4d-8e55-d6b15761f04a" /> <br>
## 📊 Dashboard
<img width="1378" height="846" alt="Screenshot 2026-01-04 131956" src="https://github.com/user-attachments/assets/8c6f040c-772b-4904-938c-b7725de060fd" /> <br>
## 📝 Book Appointment
<img width="1268" height="789" alt="Screenshot 2026-01-04 132649" src="https://github.com/user-attachments/assets/9bc12b54-dd86-4f1b-85c6-6198d8fc49f2" /> <br>
## 📅 Patient Appointments
<img width="1905" height="713" alt="Screenshot 2026-01-04 132449" src="https://github.com/user-attachments/assets/08cab8bd-813e-4881-8904-f4950cfd3238" /> <br>
## 🔄 Reschedule Appointment
<img width="1106" height="821" alt="Screenshot 2026-01-04 132913" src="https://github.com/user-attachments/assets/7e3384d8-1cf3-4b92-b5fd-d9c7a3865ed9" /> <br>
## 📄 Patient Reports
<img width="1468" height="585" alt="Screenshot 2026-01-04 133508" src="https://github.com/user-attachments/assets/7e44848f-5f1c-4335-84e5-039e2dd68700" /> <br>









