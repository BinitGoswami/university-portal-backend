# University Portal Backend

A robust Spring Boot backend application designed to manage university operations, including student enrollments, faculty assignments, and course management. 

## 🚀 Features
- **Student Management:** Create, read, update, and delete student records.
- **Course Management:** Manage course offerings and credits.
- **Faculty Management:** Assign and track faculty members and their departments.
- **Enrollment System:** Handle student enrollments into specific courses with grades and dates.
- **Security:** JWT-based authentication for secure API access.
- **API Documentation:** Interactive Swagger UI for testing endpoints.

## 🛠️ Technology Stack
- **Java 25**
- **Spring Boot 4.1.0** (WebMVC, Data JPA, Security, Validation)
- **PostgreSQL** (Database)
- **JJWT** (JSON Web Tokens)
- **Springdoc OpenAPI** (Swagger Documentation)
- **Maven** (Build Tool)

## 📊 Database Entity-Relationship (ER) Diagram

The system's database schema revolves around core entities for the university portal. Note that a student can have a direct many-to-many relationship with courses, as well as an explicit `Enrollment` record for tracking grades and dates.

```mermaid
erDiagram
    USERS {
        Long id PK
        String username UK
        String password
        String role
    }

    FACULTY {
        Long id PK
        String name
        String email UK
        String department
    }

    STUDENTS {
        Long id PK
        String name
        String email UK
        String phone
        String department
        Long faculty_id FK
    }

    COURSES {
        Long id PK
        String courseName UK
        Integer credits
    }

    ENROLLMENTS {
        Long id PK
        LocalDate enrollmentDate
        String grade
        Long student_id FK
        Long course_id FK
    }

    STUDENT_COURSE {
        Long student_id FK
        Long course_id FK
    }

    FACULTY ||--o{ STUDENTS : "mentors/advises"
    STUDENTS ||--o{ ENROLLMENTS : "has"
    COURSES ||--o{ ENROLLMENTS : "includes"
    STUDENTS }|--|{ STUDENT_COURSE : "enrolled_in"
    COURSES }|--|{ STUDENT_COURSE : "has_students"
```

## 🏗️ System Architecture

The application follows a standard layered monolithic architecture, ensuring separation of concerns:

```mermaid
flowchart TD
    Client((Client/Frontend))
    subgraph Spring Boot Application
        Security[Spring Security & JWT Filter]
        Controllers[REST Controllers]
        Services[Service Layer\nBusiness Logic]
        Repositories[Spring Data JPA Repositories]
    end
    DB[(PostgreSQL Database)]

    Client <-->|HTTP/REST| Security
    Security <--> Controllers
    Controllers <--> Services
    Services <--> Repositories
    Repositories <-->|Hibernate/JDBC| DB
```

## 🧩 Class Architecture

The internal class structure of the core domain entities:

```mermaid
classDiagram
    class User {
        -Long id
        -String username
        -String password
        -String role
    }

    class Faculty {
        -Long id
        -String name
        -String email
        -String department
        -List~Student~ students
    }

    class Student {
        -Long id
        -String name
        -String email
        -String phone
        -String department
        -Faculty faculty
        -List~Course~ courses
        -List~Enrollment~ enrollments
    }

    class Course {
        -Long id
        -String courseName
        -Integer credits
        -List~Student~ students
        -List~Enrollment~ enrollments
    }

    class Enrollment {
        -Long id
        -Student student
        -Course course
        -LocalDate enrollmentDate
        -String grade
    }

    Faculty "1" --> "*" Student : Mentors
    Student "*" <--> "*" Course : Enrolls (Many-to-Many)
    Student "1" --> "*" Enrollment : Has
    Course "1" --> "*" Enrollment : Contains
```

## ⚙️ Setup and Installation

### Prerequisites
- Java 25 installed
- PostgreSQL server running locally or remotely
- Maven

### Configuration
1. Open `src/main/resources/application.properties`.
2. Configure your database and JWT secrets. The application expects these as environment variables:
   - `DB_PASSWORD`
   - `JWT_SECRET`
   
   *Alternatively, replace `${DB_PASSWORD}` and `${JWT_SECRET}` in the properties file with your actual values for local development.*

### Running the Application
1. Open a terminal in the project root directory.
2. Build and run the project using the Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
3. The server will start on port `8081`.

## 📚 API Documentation
Once the application is running, you can interact with the API endpoints via the Swagger UI:
- **Swagger URL:** [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
