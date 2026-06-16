package com.studentmanagement.university_portal_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String courseName;

    private Integer credits;

    // --- The Relationship ---
    // Many Courses can have Many Students
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    // --- Constructors ---
    public Course() {}

    public Course(String courseName, Integer credits) {
        this.courseName = courseName;
        this.credits = credits;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
