package com.studentportal.model;

import lombok.Data;

@Data
public class JwtResponse {
  private String token;
  private String email;
  private String role;
}
