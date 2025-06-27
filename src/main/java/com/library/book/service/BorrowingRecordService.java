package com.library.book.service;

import com.library.book.dto.BorrowingRecordDTO;

import java.util.List;

public interface BorrowingRecordService {
    BorrowingRecordDTO save(BorrowingRecordDTO dto);
    List<BorrowingRecordDTO> findAll();
    BorrowingRecordDTO findById(Long id);
    BorrowingRecordDTO update(Long id, BorrowingRecordDTO dto);
    void delete(Long id);
}
