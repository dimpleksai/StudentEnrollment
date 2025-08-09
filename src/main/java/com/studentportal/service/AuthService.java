package com.studentportal.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import com.studentportal.model.LoginRequest;
import com.studentportal.model.SignupRequest;
import com.studentportal.model.User;
import com.studentportal.model.UserResponse;
import com.studentportal.repository.UserRepository;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> signup(SignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(req.getRole());
        userRepository.save(u);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> login(LoginRequest req) {
    // 1) Lookup by email
    Optional<User> opt = userRepository.findByEmail(req.getEmail());
    if (opt.isEmpty()) {
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    User user = opt.get();

    // 2) Verify raw password against stored BCrypt hash
    boolean ok = passwordEncoder.matches(req.getPassword(), user.getPassword());
    if (!ok) {
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // 3) Return a sanitized payload (no password)
    UserResponse payload = new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole()
    );
    return ResponseEntity.ok(payload);
}
}





