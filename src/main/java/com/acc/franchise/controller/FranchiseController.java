package com.acc.franchise.controller;

import com.acc.franchise.dto.FranchiseRequestDto;
import com.acc.franchise.dto.FranchiseResponseDto;
import com.acc.franchise.response.ApiResponse;
import com.acc.franchise.response.PageResponse;
import com.acc.franchise.service.FranchiseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseService service;

    public FranchiseController(FranchiseService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<FranchiseResponseDto> create(
        @RequestBody @Valid FranchiseRequestDto request
    ) {
        return ApiResponse.success(
            "Franchise created successfully",
            service.create(request)
        );
    }

    @GetMapping
    public ApiResponse<PageResponse<FranchiseResponseDto>> findAll(Pageable pageable) {
        return ApiResponse.success(
            new PageResponse<>(service.findAll(pageable))
        );
    }
}
