package com.library.book.Impl;

import com.library.book.dto.BookDTO;
import com.library.book.entity.Book;
import com.library.book.exception.BookNotFoundException;
import com.library.book.repository.BookRepository;
import com.library.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDTO save(BookDTO bookDTO) {
        logger.info("Saving new book: {}", bookDTO.getTitle());
        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .build();
        return mapToDTO(bookRepository.save(book));
    }

    @Override
    public List<BookDTO> findAll() {
        logger.info("Fetching all books from the database");
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Override
    public BookDTO update(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());

        return mapToDTO(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDTO mapToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .build();
    }
}