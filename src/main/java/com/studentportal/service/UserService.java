package com.studentportal.service;

import com.studentportal.model.User;
import com.studentportal.model.CreateUserRequest;
import com.studentportal.model.UpdateUserRequest;
import com.studentportal.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // LIST users (paged)
    public Page<User> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // GET by id
    public Optional<User> get(Long id) {
        return repo.findById(id);
    }

    // CREATE user (hash password)
    public User create(CreateUserRequest req) {

        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(req.getRole());
        return repo.save(u);
    }

    // UPDATE user (no password here)
    public Optional<User> update(Long id, UpdateUserRequest req) {
        return repo.findById(id).map(existing -> {
            // if email changes, ensure uniqueness
            if (req.getEmail() != null
                && !req.getEmail().equalsIgnoreCase(existing.getEmail())
                && repo.existsByEmail(req.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
            if (req.getName() != null) existing.setName(req.getName());
            if (req.getEmail() != null) existing.setEmail(req.getEmail());
            if (req.getRole() != null)  existing.setRole(req.getRole());
            return repo.save(existing);
        });
    }
}


