package com.studentportal.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import com.studentportal.model.LoginRequest;
import com.studentportal.model.SignupRequest;
import com.studentportal.model.User;
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
        Optional<User> u = userRepository.findByEmail(req.getEmail());
        if (u.isEmpty() || !passwordEncoder.matches(req.getPassword(), u.get().getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        return ResponseEntity.ok("Login successful");
    }
}

