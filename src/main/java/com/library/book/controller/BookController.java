package com.library.book.controller;
import com.library.book.dto.BookDTO;
import com.library.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) {
        return bookService.save(bookDTO);
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public BookDTO updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        return bookService.update(id, bookDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
