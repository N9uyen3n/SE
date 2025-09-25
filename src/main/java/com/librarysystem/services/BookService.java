package com.librarysystem.services;

import com.librarysystem.models.Book;
import com.librarysystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Integer bookId) {
        return bookRepository.findById(bookId);
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(keyword, keyword, keyword);
    }

    public Book addBook(Book book) {
        // In a real app, you'd add authorization logic here to ensure only admins can add books.
        return bookRepository.save(book);
    }

    public Book updateBook(Integer bookId, Book bookDetails) {
        // Authorization logic needed here as well.
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setGenre(bookDetails.getGenre());
        book.setQuantityInStock(bookDetails.getQuantityInStock());

        return bookRepository.save(book);
    }

    public void deleteBook(Integer bookId) {
        // Authorization logic needed here.
        bookRepository.deleteById(bookId);
    }

    public boolean isBookAvailable(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(book -> book.getQuantityInStock() > 0)
                .orElse(false);
    }
}