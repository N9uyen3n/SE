package com.librarysystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String title;
    private String author;
    private String genre;
    private int quantityInStock;

    public Book() {
    }

    public Book(int bookId, String title, String author, String genre, int quantityInStock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantityInStock = quantityInStock;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public boolean isAvailable() {
        return this.quantityInStock > 0;
    }
}