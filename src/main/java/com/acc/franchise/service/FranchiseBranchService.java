package com.acc.franchise.service;

import com.acc.franchise.domain.FranchiseBranch;
import com.acc.franchise.dto.FranchiseBranchRequestDto;
import com.acc.franchise.dto.FranchiseBranchResponseDto;
import com.acc.franchise.exception.DuplicateResourceException;
import com.acc.franchise.repository.FranchiseBranchRepository;
import com.acc.franchise.response.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FranchiseBranchService {

    private final FranchiseBranchRepository repository;

    public FranchiseBranchService(FranchiseBranchRepository repository) {
        this.repository = repository;
    }

    // Create a new branch, checking duplicates
    public Mono<FranchiseBranchResponseDto> create(FranchiseBranchRequestDto request) {
        return repository.countByNameAndFranchise(request.name(), request.franchiseId())
                .flatMap(count -> {
                    if (count != null && count > 0) {
                        return Mono.error(new DuplicateResourceException(
                                "Branch with name '" + request.name() + "' already exists in this franchise"
                        ));
                    }
                    // Create branch without manually setting ID; DB handles auto-increment
                    FranchiseBranch branch = new FranchiseBranch(request.franchiseId(), request.name());
                    return repository.save(branch)
                            .map(this::toResponse);
                });
    }

    // Get paginated branches by franchiseId
    public Mono<PageResponse<FranchiseBranchResponseDto>> findAll(Long franchiseId, int page, int size) {
        if (franchiseId == null) {
            return Mono.error(new IllegalArgumentException("franchiseId is required"));
        }

        long offset = (long) page * size;

        Mono<Long> totalCount = repository.countAllActiveByFranchise(franchiseId)
                .defaultIfEmpty(0L);

        return repository.findAllPagedByFranchise(franchiseId, size, offset)
                .map(this::toResponse)
                .collectList()
                .zipWith(totalCount, (list, total) -> new PageResponse<>(list, page, size, total))
                .onErrorResume(ex -> Mono.error(new RuntimeException(
                        "Failed to fetch branches: " + ex.getMessage(), ex)));
    }

    // Map domain to DTO
    private FranchiseBranchResponseDto toResponse(FranchiseBranch branch) {
        if (branch == null) return null;
        return new FranchiseBranchResponseDto(
                branch.getId(),
                branch.getFranchiseId(),
                branch.getName()
        );
    }
}
