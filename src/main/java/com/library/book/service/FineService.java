package com.library.book.service;

import com.library.book.dto.FineDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FineService {
    BigDecimal calculateFine(LocalDate borrowDate, LocalDate returnDate);
    List<FineDTO> getUnpaidFinesForUser(Long userId);
    void generateFineIfApplicable(Long borrowingRecordId, Long userId, LocalDate borrowDate, LocalDate returnDate);
}