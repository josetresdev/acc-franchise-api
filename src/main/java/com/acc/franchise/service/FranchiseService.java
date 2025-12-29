package com.acc.franchise.service;

import com.acc.franchise.domain.Franchise;
import com.acc.franchise.dto.FranchiseRequestDto;
import com.acc.franchise.dto.FranchiseResponseDto;
import com.acc.franchise.exception.DuplicateResourceException;
import com.acc.franchise.repository.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FranchiseService {

    private final FranchiseRepository repository;

    public FranchiseService(FranchiseRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new franchise.
     * Checks for duplicates before saving.
     */
    public Mono<FranchiseResponseDto> create(FranchiseRequestDto request) {
        return repository.countByNameAndNotDeleted(request.name())
                .flatMap(count -> {
                    if (count > 0) {
                        return Mono.error(new DuplicateResourceException(
                                "Franchise with name '" + request.name() + "' already exists"
                        ));
                    }

                    // Create new franchise (ID auto-generated)
                    Franchise franchise = Franchise.create(request.name());

                    // Save and map to response DTO
                    return repository.save(franchise)
                            .map(this::toResponse);
                });
    }

    /**
     * Returns a paginated list of franchises.
     */
    public Mono<List<FranchiseResponseDto>> findAll(int page, int size) {
        long offset = (long) page * size;

        return repository.findAllPaged(size, offset)
                .map(this::toResponse)
                .collectList();
    }

    /**
     * Maps Franchise entity to DTO
     */
    private FranchiseResponseDto toResponse(Franchise franchise) {
        return new FranchiseResponseDto(
                franchise.getId(),
                franchise.getName()
        );
    }
}
