package com.librarysystem.services;

import com.librarysystem.models.User;
import com.librarysystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // In a real application, you would use a password encoder.
    // For example, Spring Security's BCryptPasswordEncoder.
    // private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Add logic to check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }

        // Here you would encode the password before saving
        // user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // IMPORTANT: This is NOT secure! In a real application, you must use a password
        // encoder (like BCrypt) to compare hashed passwords.
        // e.g., if (!passwordEncoder.matches(password, user.getPasswordHash())) { ... }
        if (!user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    // Add login logic, etc.
}
