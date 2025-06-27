package com.library.book.Impl;

import com.library.book.dto.BorrowingRecordDTO;
import com.library.book.entity.BorrowingRecord;
import com.library.book.exception.BookNotFoundException;
import com.library.book.exception.UserNotFoundException;
import com.library.book.repository.BookRepository;
import com.library.book.repository.BorrowingRecordRepository;
import com.library.book.repository.UserRepository;
import com.library.book.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository repo;
    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public BorrowingRecordDTO save(BorrowingRecordDTO dto) {
        BorrowingRecord record = BorrowingRecord.builder()
                .book(bookRepo.findById(dto.getBookId()).orElseThrow(() -> new BookNotFoundException("Book not found")))
                .user(userRepo.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found")))
                .borrowDate(dto.getBorrowDate())
                .returnDate(dto.getReturnDate())
                .build();
        return mapToDTO(repo.save(record));
    }

    @Override
    public List<BorrowingRecordDTO> findAll() {
        return repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public BorrowingRecordDTO findById(Long id) {
        return repo.findById(id).map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
    }

    @Override
    public BorrowingRecordDTO update(Long id, BorrowingRecordDTO dto) {
        BorrowingRecord record = repo.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
        record.setBorrowDate(dto.getBorrowDate());
        record.setReturnDate(dto.getReturnDate());
        return mapToDTO(repo.save(record));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private BorrowingRecordDTO mapToDTO(BorrowingRecord r) {
        return BorrowingRecordDTO.builder()
                .id(r.getId())
                .bookId(r.getBook().getId())
                .userId(r.getUser().getId())
                .borrowDate(r.getBorrowDate())
                .returnDate(r.getReturnDate())
                .build();
    }
}

