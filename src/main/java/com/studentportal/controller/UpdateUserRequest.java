package com.studentportal.controller;

import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    // optional, keep role nullable so you can ignore if not provided
    private String role;
}

