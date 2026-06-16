package com.studentmanagement.university_portal_backend.service;

import com.studentmanagement.university_portal_backend.entity.Course;
import com.studentmanagement.university_portal_backend.entity.Student;
import com.studentmanagement.university_portal_backend.repository.CourseRepository;
import com.studentmanagement.university_portal_backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Constructor Injection (Injecting both repository)- Spring automatically provides the repository here
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // 1. Create or Update a student
    public Student saveStudent(Student student) {
        // Later, we can add logic here (e.g., checking if the email already exists)
        return studentRepository.save(student);
    }

    // 2. Get a list of all students
    public List<Student> getAllStudents() {
        return  studentRepository.findAll();
    }

    // 3. Get a specific student by their ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // 4. Delete a student
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public void enrollStudentInCourse(Long studentId, Long courseId) {
        // 1. Find the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        // 2. Find the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        // 3. Add the course to the student 's list
        student.getCourses().add(course);

        // 4. Save the student
        // (Hibernate will automatically update the student_course table!)
        studentRepository.save(student);

    }
}
