# 💼 JobHuntPro – Online Job Portal
JobHuntPro is a Spring Boot-based job portal web application that simulates real-world hiring workflows. It includes role-based functionality for Job Seekers, Employers, and Admins. Users can register, post jobs, apply for jobs, and manage profiles. The backend exposes RESTful APIs with Swagger documentation, and MySQL is used for data persistence.

##🚀 Features
👤 Job Seeker: Register, login, upload resume, apply for jobs, manage profile.

🧑‍💼 Employer: Register, post jobs, manage posted jobs, view applicants.

🛠️ Admin: Manage users, jobs, and system monitoring.

🔍 Job Search with filters (title, location, experience)

📄 Resume upload (PDF)

🧾 REST API with Swagger Docs

🔐 Role-based access and JWT-ready structure

🌐 Frontend served via static/ folder (HTML, CSS, JS)

## 🛠️ Tech Stack
Java 17

Spring Boot

Spring MVC + Spring Data JPA

MySQL

Swagger (springdoc-openapi-ui)

HTML, CSS, JavaScript

Postman (for testing APIs)

Spring Tool Suite (STS 4)

## 📁 Project Structure
jobhuntpro/
├── src/
│ ├── main/
│ │ ├── java/com/jobhuntpro/
│ │ │ ├── controller/
│ │ │ ├── entity/
│ │ │ ├── service/
│ │ │ └── repository/
│ │ └── resources/
│ │ ├── static/ (Frontend files)
│ │ └── application.properties
├── pom.xml
└── README.md

##▶️ Run the Project
 
App runs locally at:
➡️ http://localhost:8080

## 📘 Swagger API Documentation
Swagger UI available at:
➡️ http://localhost:8080/swagger-ui.html

Use Swagger to test:

POST /api/auth/register

POST /api/jobs (Employer)

GET /api/jobs (Search jobs)

POST /api/apply/{jobId} (Apply for job)

## 🔐 Sample Login Credentials
👤 Job Seeker
Username: jobseeker1
Password: jsPass1

Username: jobseeker2
Password: jsPass2

🧑‍💼 Employer
Username: employer1
Password: empPass1

Username: employer2
Password: empPass2

🛠️ Admin
Username: admin1
Password: adminPass1

Username: admin2
Password: adminPass2

## 👨‍💻 Author
Manoj S N
Java Full Stack Developer
📍 Bengaluru, India
📧 manojsn.dev@gmail.com

