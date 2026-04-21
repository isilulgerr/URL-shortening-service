# 🚀 URL Shortening Service (Spring Boot & PostgreSQL)

RESTful API built with Java and Spring Boot that helps shorten long URLs, tracks access statistics, and handles real-time HTTP redirects.

---

## ✨ Features

- **URL Shortening:** Generate a unique, random 6-character short code for any long URL.
- **Auto-Redirection:** Accessing the short URL automatically redirects the user to the original destination (HTTP 302).
- **Access Analytics:** Tracks how many times each short URL has been accessed via an `accessCount` counter.
- **Full CRUD Support:**
  - Create new short links.
  - Retrieve original URLs.
  - Update the destination of an existing short code.
  - Delete short codes from the system.
- **Persistence:** All data is stored in a local PostgreSQL database.

---

## 🛠️ Tech Stack

- **Language:** Java 17 (Eclipse Adoptium)
- **Framework:** Spring Boot 3.x (Spring Web, Spring Data JPA)
- **Database:** PostgreSQL
- **ORM:** Hibernate
- **Build Tool:** Maven
- **API Testing:** Postman / Thunder Client

---

## ⚙️ Configuration & Setup

### 1. Database Setup
Create a new database in your local PostgreSQL instance:
```sql
CREATE DATABASE url_db;
```

### 2. Application Properties
Update the `src/main/resources/application.properties` file with your local database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/url_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_LOCAL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the Application
Execute the following command in the project root directory:
```bash
mvn spring-boot:run
```

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/shorten` | Creates a new short URL. |
| **GET** | `/shorten/{shortCode}` | Redirects to the original URL and increments access count. |
| **GET** | `/shorten/{shortCode}/stats` | Returns the statistics (access count, timestamps) for a code. |
| **PUT** | `/shorten/{shortCode}` | Updates the destination URL of an existing short code. |
| **DELETE** | `/shorten/{shortCode}` | Deletes the short URL entry. |

---

## 📊 Usage Example (POST)

**Endpoint:** `http://localhost:8080/shorten`

**Request Body:**
```json
{
  "url": "https://www.github.com/isilulgerr"
}
```

**Response:**
```json
{
  "id": 1,
  "originalUrl": "https://www.github.com/isilulgerr",
  "shortCode": "vjTrxb",
  "accessCount": 0,
  "createdAt": "2026-04-21T21:45:00",
  "updatedAt": "2026-04-21T21:45:00"
}
```
