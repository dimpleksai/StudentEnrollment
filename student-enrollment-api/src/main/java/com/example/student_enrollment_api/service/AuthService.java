package com.example.student_enrollment_api.service;


import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.repo.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class AuthService {

  private final StudentRepository students;
  private final PasswordEncoder encoder;

  public AuthService(StudentRepository students, PasswordEncoder encoder) {
    this.students = students;
    this.encoder = encoder;
  }

  @Transactional
  public void signup(String name, String email, String rawPassword) {
    if (students.existsByEmail(email)) {
      throw new IllegalStateException("Email already registered");
    }
    Student s = new Student();
    s.setName(name);
    s.setEmail(email);
    s.setPasswordHash(encoder.encode(rawPassword)); // hash!
    students.save(s);
  }

  public void verifyLogin(String email, String rawPassword) {
    Student s = students.findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("Invalid credentials"));
    if (!encoder.matches(rawPassword, s.getPasswordHash())) {
      throw new NoSuchElementException("Invalid credentials");
    }
    // Later we’ll return a JWT; for now, success means “no exception thrown”.
  }
}