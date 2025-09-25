package com.librarysystem.controllers;

import com.librarysystem.controllers.dto.LoginRequest;
import com.librarysystem.models.User;
import com.librarysystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // In a real app, password should be hashed in the service layer before saving
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
            // In a real app, return a JWT token here instead of the user object
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage()); // 401 Unauthorized
        }
    }
}
