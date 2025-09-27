package com.librarysystem.services;

import com.librarysystem.models.Book;
import com.librarysystem.models.BorrowingRecord;
import com.librarysystem.models.User;
import com.librarysystem.repositories.BookRepository;
import com.librarysystem.repositories.BorrowingRecordRepository;
import com.librarysystem.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowingServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivityLogService activityLogService;

    @InjectMocks
    private BorrowingService borrowingService;

    private User user;
    private Book book;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);
        user.setUsername("testuser");

        book = new Book();
        book.setBookId(1);
        book.setTitle("Test Book");
        book.setQuantityInStock(5);

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setRecordId(1);
        borrowingRecord.setUser(user);
        borrowingRecord.setBook(book);
        borrowingRecord.setStatus(BorrowingRecord.BorrowingStatus.BORROWED);
    }

    @Test
    void testBorrowBook_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingService.borrowBook(1, 1);

        assertNotNull(result);
        assertEquals(BorrowingRecord.BorrowingStatus.BORROWED, result.getStatus());
        assertEquals(4, book.getQuantityInStock()); // Check that stock was decreased

        verify(userRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(book);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
        verify(activityLogService, times(1)).logActivity(user, "Borrowed book: Test Book");
    }

    @Test
    void testBorrowBook_BookNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingService.borrowBook(1, 1);
        });

        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, never()).save(any());
        verify(borrowingRecordRepository, never()).save(any());
    }

    @Test
    void testBorrowBook_UserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingService.borrowBook(1, 1);
        });

        assertEquals("User not found with id: 1", exception.getMessage());
        verify(bookRepository, never()).save(any());
        verify(borrowingRecordRepository, never()).save(any());
    }

    @Test
    void testBorrowBook_OutOfStock() {
        book.setQuantityInStock(0);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingService.borrowBook(1, 1);
        });

        assertEquals("Book is out of stock: Test Book", exception.getMessage());
        verify(bookRepository, never()).save(any());
        verify(borrowingRecordRepository, never()).save(any());
    }

    @Test
    void testReturnBook_Success() {
        int initialStock = book.getQuantityInStock();
        when(borrowingRecordRepository.findById(1)).thenReturn(Optional.of(borrowingRecord));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = borrowingService.returnBook(1);

        assertNotNull(result);
        assertEquals(BorrowingRecord.BorrowingStatus.RETURNED, result.getStatus());
        assertNotNull(result.getReturnDate());
        assertEquals(initialStock + 1, book.getQuantityInStock());

        verify(borrowingRecordRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(book);
        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
        verify(activityLogService, times(1)).logActivity(user, "Returned book: Test Book");
    }

    @Test
    void testReturnBook_AlreadyReturned() {
        borrowingRecord.setStatus(BorrowingRecord.BorrowingStatus.RETURNED);
        when(borrowingRecordRepository.findById(1)).thenReturn(Optional.of(borrowingRecord));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingService.returnBook(1);
        });

        assertEquals("Book has already been returned.", exception.getMessage());
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testReturnBook_RecordNotFound() {
        when(borrowingRecordRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingService.returnBook(1);
        });

        assertEquals("Borrowing record not found with id: 1", exception.getMessage());
        verify(bookRepository, never()).save(any());
    }
}
