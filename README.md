# Employee Management System

## Overview

The Employee Management System is a Java desktop application designed to simplify the employee recruitment and management process. The system enables applicants to submit their application details and allows Human Resource (HR) administrators to review, approve, reject, and manage employee records.

The project demonstrates Java programming, Object-Oriented Programming (OOP), Java Swing GUI development, JDBC database connectivity, SQL database operations, and Git version control.

---

##  Objectives

This project aims to demonstrate the following concepts:

- Java Object-Oriented Programming (OOP)
- Java Swing GUI Development
- Database Design
- SQL Queries
- JDBC Database Connectivity
- CRUD (Create, Read, Update, Delete) Operations
- Git Version Control
- GitHub Repository Management

---

##  System Features
1.Applicant can submit application details and documents.
**Equivalent in Employee Management System:** Employee Registration

The system allows an administrator or HR officer to register a new employee by submitting personal and employment details.
The system validates the information before storing it in the database.
Application Entity
        ↓
ApplicationRepository
        ↓
ApplicationRequestDTO
ApplicationResponseDTO
        ↓
ApplicationService
        ↓
ApplicationController
        ↓
POST /api/applications
The system validates all required fields.
The system verifies that the email address is unique.
If validation succeeds, the employee information is stored in the database.
Output
Employee record is successfully created.
The created employee details are returned as a JSON response.

2.Applicant can get application status by Track ID.

3.HR Admin can fetch all applications.

4.HR Admin can fetch a specific application profile.

5.HR Admin can make an application decision (Review Action).

6.HR Admin can delete an application record.

7.User can sign up (Register User).

8.User can sign in (User Login - Identity Verification Only).

9.System can update login to issue token (JWT Authorization).

10.System can protect application and admin endpoints using the issued token (Protect Routes).

### Applicant Module

- Submit employment application details.
- Upload required application documents.
- View application status.

### HR Administrator Module

- Secure login authentication.
- View all submitted applications.
- Search for a specific applicant.
- View applicant profile.
- Approve applications.
- Reject applications.
- Delete application records.
- Manage employee records.

### 🗄 Database Features

- Store applicant information.
- Store employee information.
- Maintain application status.
- Retrieve employee and applicant records.

---

## Technologies Used
Spring Boot
- PostgreSQL
- Spring Security
- JWT Authentication
- REST API
- Docker
- Java 25
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT Authentication
- PostgreSQL
- Maven
- Lombok
- Jakarta Validation
- Git & GitHub

### Programming Language
IntelliJ IDEA Community Edition
- pgAdmin 4
- Maven
- Git
- GitHub
- Postman
### Development Tools

- NetBeans IDE
- MySQL Workbench
- Git
- GitHub

---

##  Project Structure

```
## Project Structure

```
src
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── config
└── EmployeeManagementApplication.java
readme.md
```

---

##  Database Tables

The system consists of the following database tables:

- users
- applicants
- employees
- departments
## Database

The application uses MySQL as its relational database management system.

### Database Concepts Demonstrated

- Relational Database Design
- Table Creation
- Primary Keys
- Foreign Keys
- SQL Queries
- CRUD Operations
- JDBC Connectivity

### Main Database Tables

- users
- applicants
- employees
- departments
---

##  Installation

1. Clone the repository.

```bash
git clone https://github.com/kwishaka/employeemanagmentsystem.git
```

2. Open the project in NetBeans IDE.

3. Create the MySQL database.

4. Import the SQL database script.

5. Update the database connection credentials inside `DBConnection.java`.

6. Run the application.

---

##  Application Workflow

1. HR Administrator logs into the system.
2. Applicant submits an employment application.
3. Application information is stored in the database.
4. HR Administrator reviews submitted applications.
5. HR Administrator approves or rejects applications.
6. Approved applicants can be added to employee records.

---

##  Skills Demonstrated

### Git & Version Control

- Git repository creation
- Git commits
- GitHub repository management
- Version control

### Database Skills

- Database Design
- SQL Table Creation
- SQL Queries
- CRUD Operations
- JDBC Connectivity

### Java Programming

- Object-Oriented Programming (OOP)
- Java Swing GUI Development
- Exception Handling
- JDBC Programming

---

## Git & Version Control

This project demonstrates the use of Git and GitHub for version control.

### Git Skills Applied

- Repository creation
- Initial commit
- Regular commits for feature development
- Branch creation and management
- Version tracking using GitHub

### Common Git Commands

```bash
git init
git add .
git commit -m "Initial commit"
git branch development
git checkout development
git merge development
git push origin main
```

Project management activities include:

- Planning project milestones
- Tracking development progress
- Managing source code using Git
- Version control using GitHub

Future versions will integrate:

- Jira Scrum Board

- ##  Future Improvements

The following features are planned for future versions of the system:

- Employee attendance management
- Payroll management
- Email notifications
- Password encryption
- Role-based access control
- Report generation
- Migration to Spring Boot and PostgreSQL

---
- GitHub Issues
- Sprint Planning
- Task Tracking
