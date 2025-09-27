//package com.librarysystem.services;
//
//import com.librarysystem.models.Book;
//import com.librarysystem.repositories.BookRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class BookServiceTest {
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @InjectMocks
//    private BookService bookService;
//
//    private Book book;
//
//    @BeforeEach
//    void setUp() {
//        book = new Book();
//        book.setId(1);
//        book.setTitle("Test Book");
//        book.setAuthor("Test Author");
//        book.setGenre("Test Genre");
//        book.setQuantityInStock(10);
//    }
//
//    @Test
//    void testGetAllBooks() {
//        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
//
//        List<Book> books = bookService.getAllBooks();
//
//        assertNotNull(books);
//        assertEquals(1, books.size());
//        assertEquals("Test Book", books.get(0).getTitle());
//        verify(bookRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetBookById_Found() {
//        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
//
//        Optional<Book> foundBook = bookService.getBookById(1);
//
//        assertTrue(foundBook.isPresent());
//        assertEquals("Test Book", foundBook.get().getTitle());
//        verify(bookRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void testGetBookById_NotFound() {
//        when(bookRepository.findById(1)).thenReturn(Optional.empty());
//
//        Optional<Book> foundBook = bookService.getBookById(1);
//
//        assertFalse(foundBook.isPresent());
//        verify(bookRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void testSearchBooks() {
//        String keyword = "Test";
//        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(keyword, keyword, keyword))
//                .thenReturn(Collections.singletonList(book));
//
//        List<Book> books = bookService.searchBooks(keyword);
//
//        assertNotNull(books);
//        assertFalse(books.isEmpty());
//        assertEquals("Test Book", books.get(0).getTitle());
//        verify(bookRepository, times(1)).findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(keyword, keyword, keyword);
//    }
//
//    @Test
//    void testAddBook() {
//        when(bookRepository.save(any(Book.class))).thenReturn(book);
//
//        Book savedBook = bookService.addBook(new Book());
//
//        assertNotNull(savedBook);
//        assertEquals("Test Book", savedBook.getTitle());
//        verify(bookRepository, times(1)).save(any(Book.class));
//    }
//
//    @Test
//    void testUpdateBook_Found() {
//        Book updatedDetails = new Book();
//        updatedDetails.setTitle("Updated Title");
//        updatedDetails.setAuthor("Updated Author");
//        updatedDetails.setGenre("Updated Genre");
//        updatedDetails.setQuantityInStock(5);
//
//        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
//        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        Book updatedBook = bookService.updateBook(1, updatedDetails);
//
//        assertNotNull(updatedBook);
//        assertEquals("Updated Title", updatedBook.getTitle());
//        assertEquals(5, updatedBook.getQuantityInStock());
//        verify(bookRepository, times(1)).findById(1);
//        verify(bookRepository, times(1)).save(any(Book.class));
//    }
//
//    @Test
//    void testUpdateBook_NotFound() {
//        Book updatedDetails = new Book();
//        when(bookRepository.findById(1)).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> {
//            bookService.updateBook(1, updatedDetails);
//        });
//
//        verify(bookRepository, times(1)).findById(1);
//        verify(bookRepository, never()).save(any(Book.class));
//    }
//
//    @Test
//    void testDeleteBook() {
//        doNothing().when(bookRepository).deleteById(1);
//        bookService.deleteBook(1);
//        verify(bookRepository, times(1)).deleteById(1);
//    }
//
//    @Test
//    void testIsBookAvailable_True() {
//        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
//        assertTrue(bookService.isBookAvailable(1));
//        verify(bookRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void testIsBookAvailable_False() {
//        book.setQuantityInStock(0);
//        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
//        assertFalse(bookService.isBookAvailable(1));
//        verify(bookRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void testIsBookAvailable_NotFound() {
//        when(bookRepository.findById(1)).thenReturn(Optional.empty());
//        assertFalse(bookService.isBookAvailable(1));
//        verify(bookRepository, times(1)).findById(1);
//    }
//}
