package com.library.book.service;

import com.library.book.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO save(BookDTO bookDTO);
    List<BookDTO> findAll();
    BookDTO findById(Long id);
    BookDTO update(Long id, BookDTO bookDTO);
    void delete(Long id);
}