package com.studentmanagement.university_portal_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class StudentDTO {

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
    private String phone;

    private  String department;

    private Long facultyId;

    // The list of courses the student is taking
    private List<CourseDTO> enrolledCourses;

    // --- Constructors ---
    public StudentDTO() {}

    public StudentDTO(Long id, String name, String email, String phone, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Long getFacultyId() { return facultyId; }
    public void setFacultyId(Long facultyId) { this.facultyId = facultyId; }

    public List<CourseDTO> getEnrolledCourses() { return enrolledCourses; }
    public void setEnrolledCourses(List<CourseDTO> enrolledCourses) { this.enrolledCourses = enrolledCourses; }
}
