package com.acc.franchise.service;

import com.acc.franchise.domain.Franchise;
import com.acc.franchise.dto.FranchiseRequestDto;
import com.acc.franchise.dto.FranchiseResponseDto;
import com.acc.franchise.exception.DuplicateResourceException;
import com.acc.franchise.repository.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class FranchiseService {

    private final FranchiseRepository repository;

    public FranchiseService(FranchiseRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new franchise.
     */
    public Mono<FranchiseResponseDto> create(FranchiseRequestDto request) {

        return repository.existsByName(request.name())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(
                                new DuplicateResourceException(
                                        "Franchise with name '" + request.name() + "' already exists"
                                )
                        );
                    }

                    Franchise franchise = Franchise.create(request.name());

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
     * Maps domain entity to response DTO.
     */
    private FranchiseResponseDto toResponse(Franchise franchise) {
        return new FranchiseResponseDto(
                franchise.getId(),
                franchise.getUid(),
                franchise.getName()
        );
    }
}
