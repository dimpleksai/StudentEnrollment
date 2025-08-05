// package com.studentportal.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.studentportal.model.LoginRequest;
// import com.studentportal.model.SignupRequest;
// import com.studentportal.service.AuthService;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//   @Autowired private AuthService service;

//   @PostMapping("/signup")
//   public ResponseEntity<?> register(@RequestBody SignupRequest request) {
//     return service.signup(request);
//   }

//   @PostMapping("/login")
//   public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//     return service.login(request);
//   }
// }
