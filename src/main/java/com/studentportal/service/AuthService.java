// package com.studentportal.service;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.studentportal.model.JwtResponse;
// import com.studentportal.model.LoginRequest;
// import com.studentportal.model.SignupRequest;
// import com.studentportal.model.User;
// import com.studentportal.repository.UserRepository;

// @Service
// public class AuthService {

//   @Autowired private UserRepository repo;
//   @Autowired private PasswordEncoder encoder;

//   public ResponseEntity<?> signup(SignupRequest req) {
//     if (repo.existsByEmail(req.getEmail())) {
//       return ResponseEntity.badRequest().body("Email already registered");
//     }

//     User user = new User();
//     user.setName(req.getName());
//     user.setEmail(req.getEmail());
//     user.setPassword(encoder.encode(req.getPassword()));
//     user.setRole("STUDENT");

//     repo.save(user);
//     return ResponseEntity.ok("Signup successful");
//   }

//   public ResponseEntity<?> login(LoginRequest req) {
//     User user = repo.findByEmail(req.getEmail()).orElse(null);
//     if (user == null || !encoder.matches(req.getPassword(), user.getPassword())) {
//       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//     }

//     String token = JwtUtil.generateToken(user); // Add JWT logic separately
//     return ResponseEntity.ok(new JwtResponse());
//   }
// }
