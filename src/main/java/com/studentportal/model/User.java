package com.studentportal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")  // optional, but helps avoid reserved-word issues
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role;
}
