package com.studentportal.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.studentportal.model.ChangePasswordRequest;
import com.studentportal.model.SignupRequest;
import com.studentportal.model.User;
import com.studentportal.repository.UserRepository;

@Service
public class AuthService {

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    // ✅ Signup - Register new user
    public ResponseEntity<?> signup(SignupRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", "Email already in use"));
        }
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword())); // Store encrypted password
        u.setRole(req.getRole());
        userRepository.save(u);
        return ResponseEntity.ok(Map.of("status", "success", "message", "User registered successfully"));
    }

    // ✅ Login - Check credentials & return success/failure
    public ResponseEntity<?> login(String email, String rawPassword) {
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(Map.of("status", "error", "message", "Invalid email or password"));
        }

        User user = opt.get();

        boolean match = passwordEncoder.matches(rawPassword, user.getPassword());
        if (!match) {
            return ResponseEntity.status(401)
                    .body(Map.of("status", "error", "message", "Invalid email or password"));
        }

        return ResponseEntity.ok(Map.of("status", "success", "message", "Login successful"));
    }

    // ✅ Change Password - Verify old password & update
    public ResponseEntity<?> changePassword(ChangePasswordRequest req) {
        Optional<User> opt = userRepository.findByEmail(req.getEmail());
        if (opt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("status", "error", "message", "User not found"));
        }

        User user = opt.get();

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(400)
                    .body(Map.of("status", "error", "message", "Old password is incorrect"));
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("status", "success", "message", "Password changed successfully"));
    }
}
