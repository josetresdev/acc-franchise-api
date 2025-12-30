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

    public FranchiseBranchController(FranchiseBranchService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ApiResponse<FranchiseBranchResponseDto>> create(
            @RequestBody @Valid FranchiseBranchRequestDto request
    ) {
        return service.create(request)
                .map(branch -> ApiResponse.success("Branch created successfully", branch))
                .onErrorResume(ex -> Mono.just(ApiResponse.error(ex.getMessage())));
    }

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
