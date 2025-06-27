package com.library.book.controller;

import com.library.book.dto.BorrowingRecordDTO;
import com.library.book.service.BorrowingRecordService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class BorrowingRecordController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowingRecordController.class);

    @Autowired
    private BorrowingRecordService recordService;

    @PostMapping("/add")
    public ResponseEntity<BorrowingRecordDTO> addRecord(@Valid @RequestBody BorrowingRecordDTO dto) {
        logger.info("Adding new borrowing record: {}", dto);
        BorrowingRecordDTO saved = recordService.save(dto);
        logger.info("Borrowing record added with ID: {}", saved.getId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BorrowingRecordDTO>> getAllRecords() {
        logger.info("Fetching all borrowing records");
        List<BorrowingRecordDTO> list = recordService.findAll();
        logger.info("Total records fetched: {}", list.size());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecordDTO> getRecord(@PathVariable Long id) {
        logger.info("Fetching borrowing record with ID: {}", id);
        BorrowingRecordDTO record = recordService.findById(id);
        logger.info("Record fetched: {}", record);
        return ResponseEntity.ok(record);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BorrowingRecordDTO> updateRecord(@PathVariable Long id, @Valid @RequestBody BorrowingRecordDTO dto) {
        logger.info("Updating borrowing record with ID: {}", id);
        BorrowingRecordDTO updated = recordService.update(id, dto);
        logger.info("Record updated: {}", updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        logger.info("Deleting borrowing record with ID: {}", id);
        recordService.delete(id);
        logger.info("Record deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
