package com.studentportal.model;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
    private String role;
}

