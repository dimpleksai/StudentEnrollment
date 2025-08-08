package com.studentportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
  private String name;
  private String email;
  private String password;
  public String getRole() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getRole'");
  }
}
