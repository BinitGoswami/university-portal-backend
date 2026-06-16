package com.studentmanagement.university_portal_backend.controller;

import com.studentmanagement.university_portal_backend.entity.Faculty;
import com.studentmanagement.university_portal_backend.repository.FacultyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    private final FacultyRepository facultyRepository;

    public FacultyController(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty savedFaculty = facultyRepository.save(faculty);
        return new ResponseEntity<>(savedFaculty, HttpStatus.CREATED);
    }
}
