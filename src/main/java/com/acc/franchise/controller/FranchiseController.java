package com.acc.franchise.controller;

import com.acc.franchise.dto.FranchiseRequestDto;
import com.acc.franchise.dto.FranchiseResponseDto;
import com.acc.franchise.service.FranchiseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseService service;

    public FranchiseController(FranchiseService service) {
        this.service = service;
    }

    @PostMapping
    public FranchiseResponseDto create(@RequestBody @Valid FranchiseRequestDto request) {
        return service.create(request);
    }
}
