package com.studentportal.model;

// DTO
import lombok.Data;
@Data
public class CreateUserRequest {
  private String name;
  private String email;
  private String password;
  private String role;
}
