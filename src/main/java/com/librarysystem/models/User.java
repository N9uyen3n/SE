package com.librarysystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    public enum UserRole {
        ADMIN,
        USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(int userId, String username, String passwordHash, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}