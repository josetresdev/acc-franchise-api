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

                    Franchise franchise = new Franchise(request.name());

                    return repository.save(franchise)
                            .map(saved ->
                                    new FranchiseResponseDto(
                                            saved.getId(),
                                            saved.getName()
                                    )
                            );
                });
    }

    public Mono<List<FranchiseResponseDto>> findAll(int page, int size) {

        long offset = (long) page * size;

        return repository.findAllPaged(size, offset)
                .map(franchise ->
                        new FranchiseResponseDto(
                                franchise.getId(),
                                franchise.getName()
                        )
                )
                .collectList();
    }
}
