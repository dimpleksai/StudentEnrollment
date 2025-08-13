package com.example.student_enrollment_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "undergraduate_students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id")
  private Long id;

  @Column(name = "student_number", nullable = false, unique = true, length = 20)
  private String studentNumber;

  @NotBlank
  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @NotBlank
  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @Email
  @Column(name = "email", nullable = false, unique = true, length = 100)
  private String email;

  @Column(name = "classification", nullable = false, length = 15)
  private String classification; // Freshman/Sophomore/Junior/Senior

  @Column(name = "major", length = 100)
  private String major;

  @Column(name = "gpa", precision = 3, scale = 2)
  private BigDecimal gpa;

  @Column(name = "enrollment_status", length = 20)
  private String enrollmentStatus;

  @Column(name = "admission_date", nullable = false)
  private LocalDate admissionDate;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(name = "email_verified", nullable = false)
  private boolean emailVerified;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getStudentNumber() { return studentNumber; }
  public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getClassification() { return classification; }
  public void setClassification(String classification) { this.classification = classification; }

  public String getMajor() { return major; }
  public void setMajor(String major) { this.major = major; }

  public BigDecimal getGpa() { return gpa; }
  public void setGpa(BigDecimal gpa) { this.gpa = gpa; }

  public String getEnrollmentStatus() { return enrollmentStatus; }
  public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }

  public LocalDate getAdmissionDate() { return admissionDate; }
  public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

  public boolean isEmailVerified() { return emailVerified; }
  public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
