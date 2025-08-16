package com.example.student_enrollment_api.web.dto;

public record UpdatePasswordRequest(String email, String oldPassword, String newPassword) {}