package com.studentmanagement.university_portal_backend.repository;

import com.studentmanagement.university_portal_backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
