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

    /**
     * Constructor injection for FranchiseService.
     *
     * @param service instance of FranchiseService
     */
    public FranchiseController(FranchiseService service) {
        this.service = service;
    }

    // ===================== Create a new franchise =====================
    /**
     * Endpoint to create a new franchise.
     * Uses @Valid to enforce validation rules defined in FranchiseRequestDto.
     *
     * @param request FranchiseRequestDto containing franchise information
     * @return Mono<ApiResponse<FranchiseResponseDto>> with success message and created franchise
     */
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

    // ===================== List franchises with pagination =====================
    /**
     * Endpoint to retrieve a paginated list of franchises.
     *
     * @param page page number (default 0)
     * @param size page size (default 10)
     * @return Mono<ApiResponse<PageResponse<FranchiseResponseDto>>> containing paginated franchises
     */
    @GetMapping
    public Mono<ApiResponse<PageResponse<FranchiseResponseDto>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.findAll(page, size)
                .map(franchises -> buildPageResponse(franchises, page, size))
                .map(ApiResponse::success);
    }

    /**
     * Helper method to build a PageResponse from a list of franchises.
     *
     * @param content list of FranchiseResponseDto
     * @param page    current page number
     * @param size    page size
     * @return PageResponse<FranchiseResponseDto> with pagination metadata
     */
    private PageResponse<FranchiseResponseDto> buildPageResponse(
            List<FranchiseResponseDto> content,
            int page,
            int size
    ) {
        long totalElements = content.size();
        return new PageResponse<>(content, page, size, totalElements);
    }
}
