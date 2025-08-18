package com.example.student_enrollment_api.web;

import com.example.student_enrollment_api.model.Student;
import com.example.student_enrollment_api.service.AuthService;
import com.example.student_enrollment_api.service.StudentService;
import com.example.student_enrollment_api.web.dto.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.student_enrollment_api.service.AuthService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;
    private final StudentService students;

    public AuthController(AuthService auth, StudentService students) {
        this.auth = auth;
        this.students = students;
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@RequestBody SignupRequest req) {
        auth.signup(req.name(), req.email(), req.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Signup successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody LoginRequest req) {
        auth.verifyLogin(req.email(), req.password());
        return ResponseEntity.ok(new MessageResponse("Login successful"));
    }

    @PutMapping("/update-password")
    public String updatePassword(@RequestBody UpdatePasswordRequest req) {
        String email = req.email();
        String oldPassword = req.oldPassword();
        String newPassword = req.newPassword();

        // Implement password update logic here


        return "Password updated successfully for email: " + email;
    }

    // URL: POST /api/auth/students
    @PostMapping("/students")
    public ResponseEntity<Student> updateProfile(@Valid @RequestBody UpdateStudentProfileRequest req) {
        Student updated = students.updateProfile(req);
        return ResponseEntity.ok(updated);
    }



}

