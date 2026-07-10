# Employee Management System API

## Project Overview

The Employee Management System API is a RESTful backend application developed using Spring Boot and PostgreSQL. It allows applicants to submit employment applications and enables HR administrators to manage applications securely.

The system uses JWT (JSON Web Token) authentication to secure protected endpoints and follows a layered architecture using Controllers, Services, Repositories, DTOs, and Entities.

---

## Technologies Used

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

---

## System Features

### Authentication

- User can sign up (Register User).
- User can sign in (User Login – Identity Verification Only).
- System generates a JWT token after successful login.
- System protects secured endpoints using JWT Authorization.

### Applicant Features

- Applicant can submit application details and required documents.
- Applicant can track application status using a Track ID.

### HR Admin Features

- HR Admin can fetch all applications.
- HR Admin can fetch a specific application profile.
- HR Admin can review applications and make admission or rejection decisions.
- HR Admin can delete an application record.

---

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
```

---

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Login user and receive JWT token |

### Applicants

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/applications` | Submit application |
| GET | `/api/applications/{trackId}` | Track application status |

### HR Administration

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/applications` | Get all applications |
| GET | `/api/admin/applications/{id}` | Get application profile |
| PUT | `/api/admin/applications/{id}/review` | Review application |
| DELETE | `/api/admin/applications/{id}` | Delete application |

---

## Database

The project uses PostgreSQL.

Example configuration in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/employee_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## Running the Project

### Clone the repository

```bash
git clone https://github.com/kwishaka/employee-management-system.git
```

### Navigate into the project

```bash
cd employee-management-system
```

### Run the application

```bash
mvn spring-boot:run
```

The application starts at:

```
http://localhost:8080
```

---

## Testing the API

Use one of the following tools:

- Postman
- IntelliJ HTTP Client
- Insomnia

Example Registration Endpoint

```
POST http://localhost:8080/api/users/register
```

Example Login Endpoint

```
POST http://localhost:8080/api/users/login
```

---

## Git Workflow

This project follows a Git feature-branch workflow.

- Create a feature branch from `development`.
- Implement one feature.
- Commit changes.
- Push the branch.
- Open a Pull Request.
- Request code review before merging.

---

## Future Improvements

- Password encryption using BCrypt.
- JWT authentication and authorization.
- Role-Based Access Control (Applicant & HR Admin).
- File upload for applicant documents.
- Email notifications.
- Swagger/OpenAPI documentation.
- Docker deployment.

---

