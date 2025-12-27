package com.acc.franchise.service;

import com.acc.franchise.domain.Franchise;
import com.acc.franchise.dto.FranchiseRequestDto;
import com.acc.franchise.dto.FranchiseResponseDto;
import com.acc.franchise.repository.FranchiseRepository;
import org.springframework.stereotype.Service;

@Service
public class FranchiseService {

    private final FranchiseRepository repository;

    public FranchiseService(FranchiseRepository repository) {
        this.repository = repository;
    }

    public FranchiseResponseDto create(FranchiseRequestDto request) {
        // Map request DTO to entity
        Franchise franchise = new Franchise(request.name());
        // Persist entity
        Franchise saved = repository.save(franchise);
        // Map entity to response DTO
        return new FranchiseResponseDto(saved.getId(), saved.getName());
    }
}
