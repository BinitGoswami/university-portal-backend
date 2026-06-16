package com.studentmanagement.university_portal_backend.dto;

public class CourseDTO {

    private Long id;
    private String courseName;
    private Integer credits;

    public CourseDTO() {}

    public CourseDTO(Long id, String courseName, Integer credits) {
        this.id = id;
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
}
