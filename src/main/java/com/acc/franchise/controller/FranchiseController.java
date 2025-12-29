package com.acc.franchise.controller;

import com.acc.franchise.dto.FranchiseRequestDto;
import com.acc.franchise.dto.FranchiseResponseDto;
import com.acc.franchise.response.ApiResponse;
import com.acc.franchise.response.PageResponse;
import com.acc.franchise.service.FranchiseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseService service;

    public FranchiseController(FranchiseService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ApiResponse<FranchiseResponseDto>> create(
            @RequestBody @Valid FranchiseRequestDto request
    ) {
        return service.create(request)
                .map(franchise -> ApiResponse.success(
                        "Franchise created successfully",
                        franchise
                ));
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<FranchiseResponseDto>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.findAll(page, size)
                .map(franchises -> buildPageResponse(franchises, page, size))
                .map(ApiResponse::success);
    }

    private PageResponse<FranchiseResponseDto> buildPageResponse(
            List<FranchiseResponseDto> content,
            int page,
            int size
    ) {
        long totalElements = content.size();
        return new PageResponse<>(content, page, size, totalElements);
    }
}
