package com.example.student_enrollment_api.repo;

import com.example.student_enrollment_api.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
  Optional<Student> findByEmail(String email);
  boolean existsByEmail(String email);

  Optional<Student> findByStudentNumber(String studentNumber); // <-- add this
}
