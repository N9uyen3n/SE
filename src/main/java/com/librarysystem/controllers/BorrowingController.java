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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> borrowBook(@PathVariable Integer bookId, Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            BorrowingRecord record = borrowingService.borrowBook(bookId, user.getUserId());
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return/{recordId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> returnBook(@PathVariable Integer recordId) {
        try {
            BorrowingRecord record = borrowingService.returnBook(recordId);
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('ADMIN')")
    public List<BorrowingRecord> getBorrowingHistory() {
        return borrowingService.getBorrowingHistory();
    }
}
