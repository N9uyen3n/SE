package com.librarysystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;

@Entity
public class BorrowingRecord {

    public enum BorrowingStatus {
        BORROWED,
        RETURNED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recordId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private Date borrowDate;
    private Date returnDate;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

    public BorrowingRecord() {
    }

    public BorrowingRecord(int recordId, User user, Book book, Date borrowDate) {
        this.recordId = recordId;
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = null; // Initially null
        this.status = BorrowingStatus.BORROWED;
    }

    // Getters and Setters
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BorrowingStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowingStatus status) {
        this.status = status;
    }
}