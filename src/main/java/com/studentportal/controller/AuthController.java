package com.studentportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.studentportal.model.ChangePasswordRequest;
import com.studentportal.model.SignupRequest;
import com.studentportal.model.User;
import com.studentportal.repository.UserRepository;
import com.studentportal.service.AuthService;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequest request) {
        return service.signup(request);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginGet(@RequestParam String email,
                                      @RequestParam String password,
                                      HttpSession session) {
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty() || !passwordEncoder.matches(password, opt.get().getPassword())) {
            return ResponseEntity.status(401)
                    .body(Map.of("status", "error", "message", "Invalid email or password"));
        }
        User u = opt.get();
        session.setAttribute("USER_ID", u.getId());
        session.setAttribute("USER_EMAIL", u.getEmail());
        return ResponseEntity.ok(Map.of("status", "success", "message", "Login successful"));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest req) {
        return service.changePassword(req);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");
        if (userId == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("status", "error", "message", "Not logged in"));
        }
        return userRepository.findById(userId)
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of(
                        "id", u.getId(),
                        "name", u.getName(),
                        "email", u.getEmail(),
                        "role", u.getRole()
                )))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(Map.of("status", "error", "message", "User not found")));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        // Invalidate server-side session
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clear Spring Security context
        SecurityContextHolder.clearContext();

        // Expire JSESSIONID cookie
        ResponseCookie expired = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .httpOnly(true)
                .secure(false)   // set true if using HTTPS in production
                .sameSite("Lax")
                .maxAge(0)
                .build();
        response.addHeader("Set-Cookie", expired.toString());

        return ResponseEntity.ok(Map.of("status", "success", "message", "Logout successful"));
    }
}
