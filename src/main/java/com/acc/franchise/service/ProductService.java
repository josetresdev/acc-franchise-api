package com.acc.franchise.service;

import com.acc.franchise.domain.Product;
import com.acc.franchise.dto.ProductRequestDto;
import com.acc.franchise.dto.ProductResponseDto;
import com.acc.franchise.exception.DuplicateResourceException;
import com.acc.franchise.repository.ProductRepository;
import com.acc.franchise.response.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new product if it does not already exist in the branch.
     */
    public Mono<ProductResponseDto> create(ProductRequestDto request) {
        return repository.countByNameAndBranch(request.name(), request.franchiseBranchId())
                .flatMap(count -> {
                    if (count != null && count > 0) {
                        return Mono.error(new DuplicateResourceException(
                                "Product with name '" + request.name() + "' already exists in this branch"
                        ));
                    }
                    Product product = new Product(
                            request.franchiseBranchId(),
                            request.name(),
                            request.stock(),
                            request.price()
                    );
                    return repository.save(product).map(this::toResponse);
                });
    }

    /**
     * Retrieves a paginated list of products for a branch.
     */
    public Mono<PageResponse<ProductResponseDto>> findAll(Long branchId, int page, int size) {
        long offset = (long) page * size;
        Mono<Long> totalCount = repository.countAllActiveByBranch(branchId).defaultIfEmpty(0L);

        return repository.findAllPagedByBranch(branchId, size, offset)
                .map(this::toResponse)
                .collectList()
                .zipWith(totalCount, (list, total) -> new PageResponse<>(list, page, size, total));
    }

    /**
     * Updates an existing product (name, stock, price).
     */
    public Mono<ProductResponseDto> update(Long id, ProductRequestDto request) {
        return repository.findActiveById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(existing -> {
                    existing.setName(request.name());
                    existing.setStock(request.stock());
                    existing.setPrice(request.price());
                    return repository.save(existing)
                            .map(this::toResponse);
                });
    }

    /**
     * Performs logical deletion of a product.
     */
    public Mono<Void> delete(Long id) {
        return repository.softDelete(id)
                .flatMap(rows -> rows > 0 ? Mono.empty() : Mono.error(new RuntimeException("Product not found")));
    }

    /**
     * Retrieves products with maximum stock for a given franchise.
     */
    public Flux<ProductResponseDto> findMaxStockByFranchise(Long franchiseId) {
        return repository.findMaxStockByFranchise(franchiseId)
                .map(this::toResponseWithBranch);
    }

    /**
     * Maps Product entity to ProductResponseDto.
     */
    private ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
                String.valueOf(product.getId()),
                product.getFranchiseBranchId(),
                product.getName(),
                product.getStock(),
                product.getPrice()
        );
    }

    /**
     * Maps Product entity to ProductResponseDto for max-stock listing.
     */
    private ProductResponseDto toResponseWithBranch(Product product) {
        return new ProductResponseDto(
                String.valueOf(product.getId()),
                product.getFranchiseBranchId(),
                product.getName(), // solo el nombre del producto
                product.getStock(),
                product.getPrice()
        );
    }
}
