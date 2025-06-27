package com.library.book.Impl;

import com.library.book.dto.FineDTO;
import com.library.book.entity.Fine;
import com.library.book.repository.FineRepository;
import com.library.book.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private FineRepository fineRepository;

    @Override
    public BigDecimal calculateFine(LocalDate borrowDate, LocalDate returnDate) {
        LocalDate dueDate = borrowDate.plusDays(30);
        if (returnDate != null && returnDate.isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
            return BigDecimal.valueOf(daysOverdue * 5);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public List<FineDTO> getUnpaidFinesForUser(Long userId) {
        return fineRepository.findByUserIdAndPaidFalse(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void generateFineIfApplicable(Long borrowingRecordId, Long userId, LocalDate borrowDate, LocalDate returnDate) {
        if (fineRepository.existsByBorrowingRecordId(borrowingRecordId)) return;

        BigDecimal fineAmount = calculateFine(borrowDate, returnDate);
        if (fineAmount.compareTo(BigDecimal.ZERO) > 0) {
            Fine fine = Fine.builder()
                    .userId(userId)
                    .borrowingRecordId(borrowingRecordId)
                    .amount(fineAmount)
                    .dueDate(borrowDate.plusDays(30))
                    .paid(false)
                    .build();
            fineRepository.save(fine);
        }
    }

    private FineDTO mapToDTO(Fine fine) {
        return FineDTO.builder()
                .id(fine.getId())
                .userId(fine.getUserId())
                .borrowingRecordId(fine.getBorrowingRecordId())
                .amount(fine.getAmount())
                .dueDate(fine.getDueDate())
                .paymentDate(fine.getPaymentDate())
                .paid(fine.isPaid())
                .build();
    }
}
