package com.example.student_enrollment_api.web.dto;

import jakarta.validation.constraints.*;

public record UpdateStudentProfileRequest(
    @NotBlank @Email String email,
    @NotBlank String firstname,
    @NotBlank String lastname,
    @NotNull  @DecimalMin("0.00") @DecimalMax("4.00") Double gpa,
    @NotBlank String major,
    @NotBlank @Pattern(regexp = "Freshman|Sophomore|Junior|Senior|Graduate")
    String classification
) {}