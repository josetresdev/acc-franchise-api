package com.acc.franchise.controller;

import com.acc.franchise.dto.ProductRequestDto;
import com.acc.franchise.dto.ProductResponseDto;
import com.acc.franchise.response.ApiResponse;
import com.acc.franchise.response.PageResponse;
import com.acc.franchise.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductControllerTest {

    private final ProductService service;

    // Inject ProductService via constructor
    public ProductControllerTest(ProductService service) {
        this.service = service;
    }

    // ===================== Create a new product =====================
    /**
     * Endpoint to create a new product.
     * @param request ProductRequestDto containing product data
     * @return Mono wrapped ApiResponse with created ProductResponseDto
     */
    @PostMapping
    public Mono<ApiResponse<ProductResponseDto>> create(@RequestBody ProductRequestDto request) {
        return service.create(request)
                      .map(ApiResponse::success);
    }

    // ===================== List products by branch with pagination =====================
    /**
     * Endpoint to list all products of a specific branch with pagination.
     * @param branchId ID of the branch
     * @param page page number (default 0)
     * @param size page size (default 10)
     * @return Mono wrapped ApiResponse containing a PageResponse of ProductResponseDto
     */
    @GetMapping("/branch/{branchId}")
    public Mono<ApiResponse<PageResponse<ProductResponseDto>>> findAll(
            @PathVariable Long branchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.findAll(branchId, page, size)
                      .map(ApiResponse::success);
    }

    // ===================== Update an existing product =====================
    /**
     * Endpoint to update an existing product by ID.
     * @param id ID of the product to update
     * @param request ProductRequestDto containing updated product data
     * @return Mono wrapped ApiResponse with updated ProductResponseDto
     */
    @PutMapping("/{id}")
    public Mono<ApiResponse<ProductResponseDto>> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDto request
    ) {
        return service.update(id, request)
                      .map(ApiResponse::success);
    }

    // ===================== Delete a product =====================
    /**
     * Endpoint to delete a product by ID.
     * @param id ID of the product to delete
     * @return Mono wrapped ApiResponse with null data
     */
    @DeleteMapping("/{id}")
    public Mono<ApiResponse<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                      .then(Mono.just(ApiResponse.success(null)));
    }

    // ===================== Get products with maximum stock by franchise =====================
    /**
     * Endpoint to retrieve the products with the maximum stock per franchise.
     * Wraps the Flux inside Mono<ApiResponse> to avoid WebFlux response conflicts.
     * @param franchiseId ID of the franchise
     * @return Mono wrapped ApiResponse containing Flux of ProductResponseDto
     */
    @GetMapping("/franchise-max-stock")
    public Mono<ApiResponse<Flux<ProductResponseDto>>> findMaxStock(
            @RequestParam Long franchiseId
    ) {
        Flux<ProductResponseDto> productsFlux = service.findMaxStockByFranchise(franchiseId);
        return Mono.just(ApiResponse.success(productsFlux));
    }
}
