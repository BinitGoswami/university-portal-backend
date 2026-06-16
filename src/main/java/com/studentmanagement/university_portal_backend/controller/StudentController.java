package com.studentmanagement.university_portal_backend.controller;

import com.studentmanagement.university_portal_backend.dto.CourseDTO;
import com.studentmanagement.university_portal_backend.dto.StudentDTO;
import com.studentmanagement.university_portal_backend.entity.Faculty;
import com.studentmanagement.university_portal_backend.entity.Student;
import com.studentmanagement.university_portal_backend.repository.FacultyRepository;
import com.studentmanagement.university_portal_backend.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final FacultyRepository facultyRepository;

    // Injecting both the service and the faculty repository
    public StudentController(StudentService studentService, FacultyRepository facultyRepository) {
        this.studentService = studentService;
        this.facultyRepository = facultyRepository;
    }

    // 1. CREATE a Student (Now using @Valid and DTO)
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        // convert DTO to Entity so the database can save it
        Student student = mapToEntity(studentDTO);

        Student savedStudent = studentService.saveStudent(student);

        // Convert the saved Entity back to DTO to send to the client
        StudentDTO savedDTO = mapToDTO(savedStudent);
        return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
    }

    // 2. GET all students
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents()
                .stream()
                .map(this::mapToDTO) // Convert every entity in the list to a DTO
                .collect(Collectors.toList());
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 3. GET a single student by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(this::mapToDTO)
                .map(studentDTO -> new ResponseEntity<>(studentDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. DELETE a student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student deleted successfully!", HttpStatus.OK);
    }

    // 5. ENROLL a student in a course
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<String> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        studentService.enrollStudentInCourse(studentId, courseId);
        return new ResponseEntity<>("Student successfully enrolled in course!", HttpStatus.OK);
    }

    // --- Helper Methods for Mapping ---
    // Later on, libraries like MapStruct or ModelMapper can do this automatically!

    private Student mapToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setDepartment(dto.getDepartment());

        // If the frontend sent a facultyId, go find that faculty member and attach it
        if (dto.getFacultyId() != null) {
            Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                    .orElseThrow(() -> new RuntimeException("Faculty member not found with ID: " + dto.getFacultyId()));
            student.setFaculty(faculty);
        }

        return student;
    }

    private StudentDTO mapToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setDepartment(student.getDepartment());

        // Faculty Bridge:
        // If the student has a faculty member attached, pull out just the ID for the frontend
        if (student.getFaculty() != null) {
            dto.setFacultyId(student.getFaculty().getId());
        }

        // Course Bridge:
        if (student.getCourses() != null) {
            List<CourseDTO> courseDTOs = student.getCourses().stream()
                    .map(course -> new CourseDTO(course.getId(), course.getCourseName(), course.getCredits()))
                    .toList();
            dto.setEnrolledCourses(courseDTOs);
        }

        return dto;
    }
}
