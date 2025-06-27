package com.library.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FineDTO {
    private Long id;
    private Long userId;
    private Long borrowingRecordId;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private boolean paid;
}