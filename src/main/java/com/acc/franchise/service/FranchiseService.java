package com.acc.franchise.service;

import com.acc.franchise.domain.Franchise;
import com.acc.franchise.dto.FranchiseRequestDto;
import com.acc.franchise.dto.FranchiseResponseDto;
import com.acc.franchise.repository.FranchiseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FranchiseService {

    private final FranchiseRepository repository;

    public FranchiseService(FranchiseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public FranchiseResponseDto create(FranchiseRequestDto request) {
        if (repository.existsByName(request.name())) {
            throw new IllegalArgumentException(
                "Franchise with name '" + request.name() + "' already exists"
            );
        }

        Franchise franchise = new Franchise(request.name());

        Franchise saved = repository.save(franchise);

        return new FranchiseResponseDto(saved.getId(), saved.getName());
    }
}
