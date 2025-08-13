package com.example.student_enrollment_api.service;

import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.repo.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthService {

  private static final Logger log = LoggerFactory.getLogger(AuthService.class);

  private final StudentRepository students;
  private final PasswordEncoder encoder;

  public AuthService(StudentRepository students, PasswordEncoder encoder) {
    this.students = students;
    this.encoder = encoder;
  }

  // ---------- Public API ----------

  /**
   * Registers a new student account.
   * Populates all schema-required fields and hashes the password.
   * Throws IllegalStateException if email is already registered.
   */
  @Transactional
  public void signup(String name, String email, String rawPassword) {
    email = normalizeEmail(email);

    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email is required");
    }
    if (rawPassword == null || rawPassword.isBlank()) {
      throw new IllegalArgumentException("Password is required");
    }
    if (students.existsByEmail(email)) {
      throw new IllegalStateException("Email already registered");
    }

    String[] parts = splitName(name);
    String first = parts[0];
    String last  = parts[1];

    Student s = new Student();
    s.setFirstName(first);
    s.setLastName(last);
    s.setEmail(email);

    // Required by schema
    s.setStudentNumber(generateUniqueStudentNumber());
    s.setClassification("Freshman");       // default; adjust if your UI collects a value
    s.setAdmissionDate(LocalDate.now());   // default today
    s.setEnrollmentStatus("Active");       // optional; matches DB default
    s.setPasswordHash(encoder.encode(rawPassword));
    s.setEmailVerified(false);

    // Optional fields (null allowed)
    s.setMajor(null);
    s.setGpa(null);

    students.save(s);
    log.info("Created student: email={}, studentNumber={}", email, s.getStudentNumber());
  }

  /**
   * Verifies credentials. Throws NoSuchElementException on invalid login.
   * On success, returns the Student (so the controller can mint a JWT or build a profile).
   */
  @Transactional(readOnly = true)
  public Student verifyLogin(String email, String rawPassword) {
    email = normalizeEmail(email);

    Student s = students.findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("Invalid credentials"));

    if (!encoder.matches(rawPassword, s.getPasswordHash())) {
      throw new NoSuchElementException("Invalid credentials");
    }
    return s;
  }

  // ---------- Helpers ----------

  private String normalizeEmail(String email) {
    return (email == null) ? null : email.trim().toLowerCase(Locale.ROOT);
  }

  /**
   * Split a full name into first/last. Fallbacks ensure NOT NULL columns are satisfied.
   * "Alice Smith" -> ["Alice", "Smith"]
   * "Alice"       -> ["Alice", "Student"]
   * null/blank    -> ["Student", "User"]
   */
  private String[] splitName(String name) {
    if (name == null || name.isBlank()) {
      return new String[]{"Student", "User"};
    }
    String trimmed = name.trim().replaceAll("\\s+", " ");
    int i = trimmed.indexOf(' ');
    if (i < 0) return new String[]{ trimmed, "Student" };
    String first = trimmed.substring(0, i);
    String last  = trimmed.substring(i + 1);
    if (last.isBlank()) last = "Student";
    return new String[]{ first, last };
  }

  /**
   * Generate a unique student number like "UG2025####".
   * Retries a few times to avoid the UNIQUE constraint on (student_number).
   */
  private String generateUniqueStudentNumber() {
    final String year = String.valueOf(Year.now().getValue());
    for (int attempt = 0; attempt < 8; attempt++) {
      String seq = String.format("%04d", ThreadLocalRandom.current().nextInt(0, 10000));
      String candidate = "UG" + year + seq;
      Optional<Student> existing = students.findByStudentNumber(candidate);
      if (existing.isEmpty()) {
        return candidate;
      }
      log.warn("Collision on student_number={}, retrying (attempt {} of 8)", candidate, attempt + 1);
    }
    // Extremely unlikely to happen; if it does, fall back to a wider range
    String seq = String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
    String candidate = "UG" + year + seq;
    log.warn("Using extended-range student_number={}", candidate);
    return candidate;
  }
}
