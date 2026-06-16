package com.studentmanagement.university_portal_backend.repository;

import com.studentmanagement.university_portal_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    // Spring Data JPA automatically provides methods like:
    // save(), findById(), findAll(), deleteById()

    // We can also define custom queries simply by naming them correctly!
    // For example, Spring will automatically write the SQL for this:

    boolean existsByEmail(String email);
}
