package com.example.student_enrollment_api.web;

import com.example.student_enrollment_api.service.AuthService;
import com.example.student_enrollment_api.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.student_enrollment_api.service.AuthService;
import com.example.student_enrollment_api.web.dto.MessageResponse;
import com.example.student_enrollment_api.web.dto.UpdatePasswordRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
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
}

