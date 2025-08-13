package com.example.student_enrollment_api.service;

import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.repo.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

  private final StudentRepository students;
  private final PasswordEncoder encoder;
  private final Random random = new Random();

  public AuthService(StudentRepository students, PasswordEncoder encoder) {
    this.students = students;
    this.encoder = encoder;
  }

  // --- Utilities ---

  /** Very simple student number generator: e.g., UG2025xxxx (unique best-effort). */
  private String generateStudentNumber() {
    String year = String.valueOf(Year.now().getValue());
    String seq = String.format("%04d", random.nextInt(10000));
    String candidate = "UG" + year + seq;
    // Basic de-duplication loop (rarely iterates)
    while (students.findByStudentNumber(candidate).isPresent()) {
      seq = String.format("%04d", random.nextInt(10000));
      candidate = "UG" + year + seq;
    }
    return candidate;
  }

  private static String[] splitName(String name) {
    if (name == null || name.isBlank()) return new String[]{"Student","User"};
    String trimmed = name.trim().replaceAll("\\s+"," ");
    int i = trimmed.indexOf(' ');
    if (i < 0) return new String[]{trimmed, ""};
    return new String[]{ trimmed.substring(0,i), trimmed.substring(i+1) };
  }

  // --- Commands ---

  @Transactional
  public void signup(String name, String email, String rawPassword) {
    if (students.existsByEmail(email)) {
      throw new IllegalStateException("Email already registered");
    }

    String[] parts = splitName(name);
    String first = parts[0];
    String last  = parts[1].isBlank() ? "Student" : parts[1];

    Student s = new Student();
    s.setFirstName(first);
    s.setLastName(last);
    s.setEmail(email);

    // Required by schema
    s.setStudentNumber(generateStudentNumber());
    s.setClassification("Freshman");             // default classification
    s.setAdmissionDate(LocalDate.now());         // today by default
    s.setEnrollmentStatus("Active");             // optional: DB default also set
    s.setPasswordHash(encoder.encode(rawPassword));
    s.setEmailVerified(false);                   // until you add email verification

    // Optional niceties
    s.setMajor(null);
    s.setGpa(null);

    students.save(s);
  }

  /** Throws NoSuchElementException if invalid; returns silently on success. */
  public void verifyLogin(String email, String rawPassword) {
    Student s = students.findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("Invalid credentials"));

    if (!encoder.matches(rawPassword, s.getPasswordHash())) {
      throw new NoSuchElementException("Invalid credentials");
    }
    // Success: no exception. You can return a DTO or issue a JWT here.
  }
}
