package com.acc.franchise.service;

import com.acc.franchise.domain.Product;
import com.acc.franchise.dto.ProductRequestDto;
import com.acc.franchise.dto.ProductResponseDto;
import com.acc.franchise.exception.DuplicateResourceException;
import com.acc.franchise.repository.ProductRepository;
import com.acc.franchise.response.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Mono<ProductResponseDto> create(ProductRequestDto request) {
        return repository.countByNameAndBranch(request.name(), request.franchiseBranchId())
                .flatMap(count -> {
                    if (count > 0) {
                        return Mono.error(new DuplicateResourceException(
                                "Product with name '" + request.name() + "' already exists in this branch"
                        ));
                    }
                    Product product = Product.create(request.franchiseBranchId(), request.name(), request.stock(), request.price());
                    return repository.save(product).map(this::toResponse);
                });
    }

    public Mono<PageResponse<ProductResponseDto>> findAll(String branchId, int page, int size) {
        long offset = (long) page * size;

        Mono<Long> totalCount = repository.countAllActiveByBranch(branchId);
        return repository.findAllPagedByBranch(branchId, size, offset)
                .map(this::toResponse)
                .collectList()
                .zipWith(totalCount, (list, total) -> new PageResponse<>(list, page, size, total));
    }

    private ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getUid(),
                product.getFranchiseBranchId(),
                product.getName(),
                product.getStock(),
                product.getPrice()
        );
    }
}
