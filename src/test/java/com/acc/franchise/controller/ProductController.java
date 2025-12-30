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
@RequestMapping("/api/products") // Base URL de todos los endpoints
public class ProductController {

    private final ProductService service;

    // Inyección de ProductService mediante constructor
    public ProductController(ProductService service) {
        this.service = service;
    }

    // ===================== Crear un producto =====================
    @PostMapping
    public Mono<ApiResponse<ProductResponseDto>> create(@RequestBody ProductRequestDto request) {
        return service.create(request)
                      .map(ApiResponse::success);
    }

    // ================= Listar productos por sucursal con paginación =================
    @GetMapping("/branch/{branchId}")
    public Mono<ApiResponse<PageResponse<ProductResponseDto>>> findAll(
            @PathVariable Long branchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.findAll(branchId, page, size)
                      .map(ApiResponse::success);
    }

    // ===================== Actualizar un producto =====================
    @PutMapping("/{id}")
    public Mono<ApiResponse<ProductResponseDto>> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDto request
    ) {
        return service.update(id, request)
                      .map(ApiResponse::success);
    }

    // ===================== Eliminar un producto =====================
    @DeleteMapping("/{id}")
    public Mono<ApiResponse<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                      .then(Mono.just(ApiResponse.success(null)));
    }

    // ===================== Obtener productos con máximo stock por franquicia =====================
    @GetMapping("/franchise-max-stock")
    public Mono<ApiResponse<Flux<ProductResponseDto>>> findMaxStock(
            @RequestParam Long franchiseId
    ) {
        // En WebFlux, conviene envolver un Flux dentro de Mono<ApiResponse> para evitar conflictos
        Flux<ProductResponseDto> productsFlux = service.findMaxStockByFranchise(franchiseId);
        return Mono.just(ApiResponse.success(productsFlux));
    }
}
