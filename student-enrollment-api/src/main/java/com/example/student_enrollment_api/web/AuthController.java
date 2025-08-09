package com.example.student_enrollment_api.web;

import com.example.student_enrollment_api.service.AuthService;
import com.example.student_enrollment_api.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
