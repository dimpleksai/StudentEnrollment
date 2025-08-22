package com.example.student_enrollment_api.web;

import com.example.student_enrollment_api.web.dto.MessageResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 409 for explicit business conflicts you throw as IllegalStateException
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<MessageResponse> illegalState(IllegalStateException ex) {
    log.warn("Business conflict: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new MessageResponse(ex.getMessage())); // e.g. "Email already registered"
  }

  // 409 for unique key / FK violations etc.
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<MessageResponse> dataIntegrity(DataIntegrityViolationException ex) {
    log.error("Data integrity violation", ex);
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new MessageResponse("Conflict: Duplicate or invalid data"));
  }

  // 400 for @Valid body validation failures on DTOs
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgNotValid(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      errors.put(fe.getField(), fe.getDefaultMessage());
    }
    log.warn("Validation failed: {}", errors);
    return ResponseEntity.badRequest().body(Map.of(
        "error", "Validation failed",
        "details", errors
    ));
  }

  // 400 for @Validated on query params/path variables, etc.
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
    log.warn("Constraint violation: {}", ex.getMessage());
    return ResponseEntity.badRequest().body(Map.of(
        "error", "Constraint violation",
        "details", ex.getMessage()
    ));
  }

  // 400 for binding errors not covered by MethodArgumentNotValidException (e.g., form/data binding)
  @ExceptionHandler(BindException.class)
  public ResponseEntity<Object> handleBindException(BindException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      errors.put(fe.getField(), fe.getDefaultMessage());
    }
    log.warn("BindException: {}", errors);
    return ResponseEntity.badRequest().body(Map.of(
        "error", "Binding failed",
        "details", errors
    ));
  }

  // 422 for malformed JSON, wrong types, unreadable body, etc.
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<MessageResponse> handleNotReadable(HttpMessageNotReadableException ex) {
    log.warn("Unreadable request body: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(new MessageResponse("Malformed or unreadable request body"));
  }

  // 401 for things you currently map via NoSuchElementException (e.g., invalid credentials)
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<MessageResponse> noSuch(NoSuchElementException ex) {
    log.warn("NoSuchElement: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new MessageResponse(ex.getMessage())); // e.g. "Invalid credentials"
  }

// Handles ResponseStatusException thrown explicitly in services/controllers.
// Without this, these exceptions fall into the generic Exception handler and
// always return HTTP 500. By catching them here, we preserve the original
// status code (e.g., 404 Not Found, 400 Bad Request) and return the correct
// message to the client

   @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<MessageResponse> handleResponseStatus(ResponseStatusException ex) {
    log.warn("ResponseStatusException: {} {}", ex.getStatusCode(), ex.getReason());
    return ResponseEntity
        .status(ex.getStatusCode())
        .body(new MessageResponse(
            ex.getReason() != null ? ex.getReason() : ex.getMessage()
        ));
  }

  // 500 catch-all (logs full stack trace)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<MessageResponse> generic(Exception ex) {
    log.error("Unhandled exception", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new MessageResponse("Internal error"));
  }
}
