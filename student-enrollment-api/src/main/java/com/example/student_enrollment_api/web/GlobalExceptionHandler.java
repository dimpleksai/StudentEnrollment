package com.example.student_enrollment_api.web;

import com.example.student_enrollment_api.web.dto.MessageResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<MessageResponse> illegalState(IllegalStateException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new MessageResponse(ex.getMessage())); // e.g. "Email already registered"
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<MessageResponse> dataIntegrity(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new MessageResponse("Conflict: Duplicate or invalid data"));
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<MessageResponse> noSuch(NoSuchElementException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new MessageResponse(ex.getMessage())); // e.g. "Invalid credentials"
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<MessageResponse> generic(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new MessageResponse("Internal error"));
  }
}
