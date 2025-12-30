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

    /**
     * Constructor injection for ProductService.
     *
     * @param service ProductService instance
     */
    public ProductController(ProductService service) {
        this.service = service;
    }

    // ===================== Create a new product =====================
    /**
     * Endpoint to create a new product.
     * Uses @Valid to enforce validation rules defined in ProductRequestDto.
     * Handles errors gracefully and returns a standardized ApiResponse.
     *
     * @param request ProductRequestDto containing product information
     * @return Mono<ApiResponse<ProductResponseDto>> with success or error message
     */
    @PostMapping
    public Mono<ApiResponse<ProductResponseDto>> create(@RequestBody @Valid ProductRequestDto request) {
        return service.create(request)
                .map(p -> ApiResponse.success("Product created successfully", p))
                .onErrorResume(ex -> Mono.just(ApiResponse.error(ex.getMessage())));
    }

    // ===================== List products by branch with pagination =====================
    /**
     * Endpoint to retrieve a paginated list of products for a specific branch.
     * Handles errors gracefully and returns a standardized ApiResponse.
     *
     * @param branchId ID of the branch to retrieve products from
     * @param page     page number (default 0)
     * @param size     page size (default 10)
     * @return Mono<ApiResponse<PageResponse<ProductResponseDto>>> with success or error
     */
    @GetMapping
    public Mono<ApiResponse<PageResponse<ProductResponseDto>>> findAll(
            @RequestParam Long branchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.findAll(branchId, page, size)
                .map(ApiResponse::success)
                .onErrorResume(ex -> Mono.just(ApiResponse.error(ex.getMessage())));
    }
}
