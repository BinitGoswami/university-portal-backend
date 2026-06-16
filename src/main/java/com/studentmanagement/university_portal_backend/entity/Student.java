package com.studentmanagement.university_portal_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String department;

    // --- The Relationship ---
    // Many Students belong to One Faculty
    @ManyToOne
    @JoinColumn(name = "faculty_id") // This creates the actual Foreign Key column in PostgreSQL
    private Faculty faculty;

    @ManyToMany
    @JoinTable(
            name = "student_course", // The name of the hidden 3rd table
            joinColumns = @JoinColumn(name = "student_id"), // The column for the student
            inverseJoinColumns = @JoinColumn(name = "course_id") // The column for the course
    )
    private List<Course> courses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

    // ---- Constructors ----

    public Student() {
    }

    public Student(String name, String email, String phone, String department) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void  setId(Long id) {this.id = id; }

    public String getName() { return name; }
    public void  setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void  setEmail(String email) {this.email = email; }

    public String getPhone() { return phone; }
    public void  setPhone(String phone) {this.phone = phone; }

    public String getDepartment() { return department; }
    public void  setDepartment(String department) {this.department = department; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }
}