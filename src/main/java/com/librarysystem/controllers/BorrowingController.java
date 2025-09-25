package com.librarysystem.controllers;

import com.librarysystem.models.BorrowingRecord;
import com.librarysystem.services.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/{bookId}/user/{userId}")
    public ResponseEntity<?> borrowBook(@PathVariable Integer bookId, @PathVariable Integer userId) {
        try {
            BorrowingRecord record = borrowingService.borrowBook(bookId, userId);
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return/{recordId}")
    public ResponseEntity<?> returnBook(@PathVariable Integer recordId) {
        try {
            BorrowingRecord record = borrowingService.returnBook(recordId);
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/history")
    public List<BorrowingRecord> getBorrowingHistory() {
        return borrowingService.getBorrowingHistory();
    }
}
