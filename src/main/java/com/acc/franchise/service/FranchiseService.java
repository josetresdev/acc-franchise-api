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
        // Validar si ya existe franquicia con el mismo nombre
        if (repository.existsByName(request.name())) {
            throw new IllegalArgumentException(
                "Franchise with name '" + request.name() + "' already exists"
            );
        }

        // Map request DTO a entidad
        Franchise franchise = new Franchise(request.name());

        // Persistir entidad
        Franchise saved = repository.save(franchise);

        // Map entity a response DTO
        return new FranchiseResponseDto(saved.getId(), saved.getName());
    }
}
