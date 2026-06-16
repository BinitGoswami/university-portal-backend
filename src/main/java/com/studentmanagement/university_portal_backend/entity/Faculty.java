package com.studentmanagement.university_portal_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String department;

    // --- The Relationship ---
    // One Faculty has Many Students
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Student> students;

    // --- Constructors ---
    public Faculty() {}

    public  Faculty (String name, String email, String department) {
        this.name = name;
        this.email = email;
        this.department = department;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
