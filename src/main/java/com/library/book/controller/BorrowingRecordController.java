package com.library.book.controller;

import com.library.book.dto.BorrowingRecordDTO;
import com.library.book.service.BorrowingRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService recordService;

    @PostMapping("/add")
    public BorrowingRecordDTO addRecord(@Valid @RequestBody BorrowingRecordDTO dto) {
        return recordService.save(dto);
    }

    @GetMapping
    public List<BorrowingRecordDTO> getAllRecords() {
        return recordService.findAll();
    }

    @GetMapping("/{id}")
    public BorrowingRecordDTO getRecord(@PathVariable Long id) {
        return recordService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public BorrowingRecordDTO updateRecord(@PathVariable Long id, @Valid @RequestBody BorrowingRecordDTO dto) {
        return recordService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRecord(@PathVariable Long id) {
        recordService.delete(id);
    }
}

