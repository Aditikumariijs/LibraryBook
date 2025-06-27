package com.library.book.controller;

import com.library.book.dto.BookDTO;
import com.library.book.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        logger.info("Received request to add book: {}", bookDTO);
        BookDTO savedBook = bookService.save(bookDTO);
        logger.info("Book added with ID: {}", savedBook.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        logger.info("Fetching all books");
        List<BookDTO> books = bookService.findAll();
        logger.info("Total books fetched: {}", books.size());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        logger.info("Fetching book with ID: {}", id);
        BookDTO book = bookService.findById(id);
        logger.info("Fetched book: {}", book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        logger.info("Updating book with ID: {}", id);
        BookDTO updatedBook = bookService.update(id, bookDTO);
        logger.info("Book updated: {}", updatedBook);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        logger.info("Deleting book with ID: {}", id);
        bookService.delete(id);
        logger.info("Book deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
