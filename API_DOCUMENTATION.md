# Employee Management System API Documentation

## Version

v1.0

## Base URL

```
https://employeemanagementsystem-production-4ff0.up.railway.app
```

---

# Authentication API

## 1. Register User

Creates a new HR Administrator account.

### Endpoint

```
POST /api/auth/register
```

### Request Headers

| Header | Value |
|----------|--------|
| Content-Type | application/json |

### Request Body

```json
{
    "username":"admin",
    "password":"admin123",
    "email":"admin@gmail.com",
    "fullName":"Admin User"
}
```

### Success Response (200)

```json
{
    "token":"generated-token",
    "username":"admin",
    "email":"admin@gmail.com",
    "fullName":"Admin User",
    "role":"HR_ADMIN",
    "userId":1,
    "message":"Registration successful"
}
```

---

## 2. Login

Authenticates an existing administrator.

### Endpoint

```
POST /api/auth/login
```

### Request Body

```json
{
    "username":"admin",
    "password":"admin123"
}
```

### Success Response

```json
{
    "token":"generated-token",
    "username":"admin",
    "email":"admin@gmail.com",
    "fullName":"Admin User",
    "role":"HR_ADMIN",
    "userId":1,
    "message":"Login successful"
}
```

---

# Job Application API

## 3. Submit Application

Submits a new employee application together with supporting documents.

### Endpoint

```
POST /api/applications/submit
```

### Content Type

```
multipart/form-data
```

### Form Fields

| Field | Type | Required |
|--------|------|----------|
| application | JSON | Yes |
| resume | PDF/File | Yes |
| idDocument | PDF/Image | Yes |

### Example JSON (application)

```json
{
    "fullName":"John Doe",
    "email":"john@gmail.com",
    "phone":"0788123456",
    "position":"Software Developer",
    "education":"Bachelor Degree",
    "experience":"2 Years"
}
```

### Success Response (201 Created)

```json
{
    "trackingId":"APP-001",
    "status":"PENDING",
    "message":"Application submitted successfully"
}
```

---

## 4. Track Application

Returns the current application status.

### Endpoint

```
GET /api/applications/track/{trackingId}
```

### Example

```
GET /api/applications/track/APP-001
```

### Success Response

```json
{
    "trackingId":"APP-001",
    "status":"PENDING",
    "reviewedBy":null,
    "reviewedAt":null
}
```

---

# Admin API

**Authentication Required**

```
Authorization: Bearer <token>
```

Only users with role:

```
HR_ADMIN
```

can access these endpoints.

---

## 5. Get All Applications

### Endpoint

```
GET /api/admin/applications
```

### Response

```json
[
    {
        "id":1,
        "fullName":"John Doe",
        "email":"john@gmail.com",
        "position":"Developer",
        "status":"PENDING"
    }
]
```

---

## 6. Get Applications by Status

### Endpoint

```
GET /api/admin/applications/status/{status}
```

### Example

```
GET /api/admin/applications/status/PENDING
```

---

## 7. Get Application by ID

### Endpoint

```
GET /api/admin/applications/{id}
```

### Example

```
GET /api/admin/applications/1
```

---

## 8. Review Application

Updates an application after HR review.

### Endpoint

```
PUT /api/admin/applications/{id}/review
```

### Request Body

```json
{
    "decision":"ADMITTED",
    "notes":"Candidate meets all requirements.",
    "reviewer":"HR Manager"
}
```

### Success Response

```json
{
    "message":"Application reviewed successfully."
}
```
---

## 9. Delete Application

Deletes an application.

### Endpoint

```
DELETE /api/admin/applications/{id}
```

### Example

```
DELETE /api/admin/applications/1
```

### Success Response

```
204 No Content
```
---

## 10. Application Statistics

Returns application statistics for the dashboard.

### Endpoint

```
GET /api/admin/stats
```
### Example Response

```json
{
    "totalApplications":50,
    "pending":20,
    "reviewed":10,
    "admitted":15,
    "rejected":5
}
```

---

# Authentication

Include the authentication token for all protected endpoints.

```
Authorization: Bearer <token>
```

---

# User Roles

| Role | Description |
|------|-------------|
| HR_ADMIN | Human Resource Administrator |

---

# Application Status

| Status | Description |
|----------|--------------|
| PENDING | Waiting for HR review |
| REVIEWED | Reviewed |
| ADMITTED | Accepted |
| REJECTED | Rejected |

---

# HTTP Status Codes

| Code | Meaning |
|------|----------|
|200|Success|
|201|Created|
|204|Deleted Successfully|
|400|Bad Request|
|401|Unauthorized|
|403|Forbidden|
|404|Not Found|
|500|Internal Server Error|

---

# Technology Stack

- Java 21
- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- Hibernate
- Maven
- Railway Cloud
- GitHub
- REST API