package com.library.book.repository;

import com.library.book.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findByUserIdAndPaidFalse(Long userId);
    boolean existsByBorrowingRecordId(Long borrowingRecordId);
}

