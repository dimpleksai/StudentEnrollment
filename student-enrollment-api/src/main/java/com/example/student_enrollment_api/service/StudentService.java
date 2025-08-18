// src/main/java/com/example/student_enrollment_api/service/StudentService.java
package com.example.student_enrollment_api.service;

import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.repo.StudentRepository;
import com.example.student_enrollment_api.web.dto.UpdateStudentProfileRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Student updateProfile(UpdateStudentProfileRequest req) {
        var student = repo.findByEmail(req.email())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Student not found for email: " + req.email()));

        student.setFirstName(req.firstname());
        student.setLastName(req.lastname());
        student.setGpa(BigDecimal.valueOf(req.gpa()));
        student.setMajor(req.major());
        student.setClassification(req.classification());

        return repo.save(student);
    }
}
