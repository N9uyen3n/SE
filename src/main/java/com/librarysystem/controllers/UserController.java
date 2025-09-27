package com.librarysystem.controllers;

import com.librarysystem.models.BorrowingRecord;
import com.librarysystem.models.User;
import com.librarysystem.repositories.UserRepository;
import com.librarysystem.services.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me/history")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BorrowingRecord>> getMyBorrowingHistory(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<BorrowingRecord> history = borrowingService.getHistoryForUser(user);
        return ResponseEntity.ok(history);
    }
}
