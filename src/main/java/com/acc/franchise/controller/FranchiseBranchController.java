package com.acc.franchise.controller;

import com.acc.franchise.dto.FranchiseBranchRequestDto;
import com.acc.franchise.dto.FranchiseBranchResponseDto;
import com.acc.franchise.response.ApiResponse;
import com.acc.franchise.response.PageResponse;
import com.acc.franchise.service.FranchiseBranchService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
public class FranchiseBranchController {

    private final FranchiseBranchService service;

    /**
     * Constructor injection for FranchiseBranchService.
     *
     * @param service instance of FranchiseBranchService
     */
    public FranchiseBranchController(FranchiseBranchService service) {
        this.service = service;
    }

    // ===================== Create a new branch =====================
    /**
     * Endpoint to create a new franchise branch.
     * Uses @Valid to enforce validation rules defined in FranchiseBranchRequestDto.
     * Handles errors gracefully and returns a standardized ApiResponse.
     *
     * @param request FranchiseBranchRequestDto containing branch information
     * @return Mono<ApiResponse<FranchiseBranchResponseDto>> with success or error message
     */
    @PostMapping
    public Mono<ApiResponse<FranchiseBranchResponseDto>> create(
            @RequestBody @Valid FranchiseBranchRequestDto request
    ) {
        return service.create(request)
                .map(branch -> ApiResponse.success("Branch created successfully", branch))
                .onErrorResume(ex -> Mono.just(ApiResponse.error(ex.getMessage())));
    }

    // ===================== List branches by franchise with pagination =====================
    /**
     * Endpoint to retrieve a paginated list of branches for a specific franchise.
     * Validates the 'franchiseId' parameter and handles errors gracefully.
     *
     * @param franchiseId ID of the franchise (required)
     * @param page        page number (default 0)
     * @param size        page size (default 10)
     * @return Mono<ApiResponse<PageResponse<FranchiseBranchResponseDto>>> with success or error
     */
    @GetMapping
    public Mono<ApiResponse<PageResponse<FranchiseBranchResponseDto>>> findAll(
            @RequestParam(name = "franchiseId") Long franchiseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (franchiseId == null) {
            return Mono.just(ApiResponse.error("Parameter 'franchiseId' is required"));
        }

        return service.findAll(franchiseId, page, size)
                .map(ApiResponse::success)
                .onErrorResume(ex -> Mono.just(ApiResponse.error(ex.getMessage())));
    }
}
