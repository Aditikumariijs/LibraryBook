package com.library.book.controller;

import com.library.book.dto.FineDTO;
import com.library.book.service.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @GetMapping("/user/{userId}")
    public List<FineDTO> getFinesForUser(@PathVariable Long userId) {
        return fineService.getUnpaidFinesForUser(userId);
    }
}