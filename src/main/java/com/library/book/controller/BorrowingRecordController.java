package com.library.book.controller;

import com.library.book.dto.BorrowingRecordDTO;
import com.library.book.service.BorrowingRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService recordService;

    @PostMapping("/add")
    public ResponseEntity<BorrowingRecordDTO> addRecord(@Valid @RequestBody BorrowingRecordDTO dto) {
        BorrowingRecordDTO saved = recordService.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BorrowingRecordDTO>> getAllRecords() {
        List<BorrowingRecordDTO> list = recordService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecordDTO> getRecord(@PathVariable Long id) {
        BorrowingRecordDTO record = recordService.findById(id);
        return ResponseEntity.ok(record);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BorrowingRecordDTO> updateRecord(@PathVariable Long id, @Valid @RequestBody BorrowingRecordDTO dto) {
        BorrowingRecordDTO updated = recordService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
