package com.librarysystem.repositories;

import com.librarysystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Finds books by title, author, or genre, ignoring case.
     * @param keyword the search term
     * @return a list of matching books
     */
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(String keyword, String keyword2, String keyword3);
}