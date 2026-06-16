package com.studentmanagement.university_portal_backend.controller;

import com.studentmanagement.university_portal_backend.entity.Course;
import com.studentmanagement.university_portal_backend.entity.Enrollment;
import com.studentmanagement.university_portal_backend.entity.Student;
import com.studentmanagement.university_portal_backend.repository.CourseRepository;
import com.studentmanagement.university_portal_backend.repository.EnrollmentRepository;
import com.studentmanagement.university_portal_backend.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                StudentRepository studentRepository,
                                CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // Endpoint: POST /api/enrollments/student/1/course/2
    @PostMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<String> enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        Optional<Student> studentOpt =  studentRepository.findById(studentId);
        Optional<Course> courseOpt =  courseRepository.findById(courseId);

        if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
            return new ResponseEntity<>("Student or Course not found!", HttpStatus.NOT_FOUND);
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(studentOpt.get());
        enrollment.setCourse(courseOpt.get());
        enrollment.setGrade("Not Graded"); // Default value

        enrollmentRepository.save(enrollment);

        return new ResponseEntity<>("Student successfully enrolled in course!", HttpStatus.CREATED);
    }
}
