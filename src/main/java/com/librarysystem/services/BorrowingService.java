package com.librarysystem.services;

import com.librarysystem.models.Book;
import com.librarysystem.models.BorrowingRecord;
import com.librarysystem.models.User;
import com.librarysystem.repositories.BookRepository;
import com.librarysystem.repositories.BorrowingRecordRepository;
import com.librarysystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityLogService activityLogService;

    @Transactional
    public BorrowingRecord borrowBook(Integer bookId, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        if (book.getQuantityInStock() <= 0) {
            throw new RuntimeException("Book is out of stock: " + book.getTitle());
        }

        // Decrease stock and save
        book.setQuantityInStock(book.getQuantityInStock() - 1);
        bookRepository.save(book);

        // Create borrowing record
        BorrowingRecord record = new BorrowingRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(new Date());
        record.setStatus(BorrowingRecord.BorrowingStatus.BORROWED);
        BorrowingRecord savedRecord = borrowingRecordRepository.save(record);

        // Log activity
        activityLogService.logActivity(user, "Borrowed book: " + book.getTitle());

        return savedRecord;
    }

    @Transactional
    public BorrowingRecord returnBook(Integer recordId) {
        BorrowingRecord record = borrowingRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found with id: " + recordId));

        if (record.getStatus() == BorrowingRecord.BorrowingStatus.RETURNED) {
            throw new RuntimeException("Book has already been returned.");
        }

        // Update record status
        record.setReturnDate(new Date());
        record.setStatus(BorrowingRecord.BorrowingStatus.RETURNED);

        // Increase book stock
        Book book = record.getBook();
        book.setQuantityInStock(book.getQuantityInStock() + 1);
        bookRepository.save(book);

        // Log activity
        activityLogService.logActivity(record.getUser(), "Returned book: " + book.getTitle());

        return borrowingRecordRepository.save(record);
    }

    public List<BorrowingRecord> getBorrowingHistory() {
        return borrowingRecordRepository.findAll();
    }

    public List<BorrowingRecord> getHistoryForUser(User user) {
        return borrowingRecordRepository.findByUser(user);
    }
}
