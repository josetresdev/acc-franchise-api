package com.acc.franchise.controller;

import com.acc.franchise.dto.ProductRequestDto;
import com.acc.franchise.dto.ProductResponseDto;
import com.acc.franchise.response.ApiResponse;
import com.acc.franchise.response.PageResponse;
import com.acc.franchise.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ApiResponse<ProductResponseDto>> create(@RequestBody @Valid ProductRequestDto request) {
        return service.create(request)
                .map(p -> ApiResponse.success("Product created successfully", p));
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<ProductResponseDto>>> findAll(
            @RequestParam String branchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.findAll(branchId, page, size)
                .map(ApiResponse::success);
    }
}
