package com.library.book.controller;

import com.library.book.dto.FineDTO;
import com.library.book.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FineDTO>> getFinesForUser(@PathVariable Long userId) {
        List<FineDTO> fines = fineService.getUnpaidFinesForUser(userId);
        return ResponseEntity.ok(fines);
    }
}
