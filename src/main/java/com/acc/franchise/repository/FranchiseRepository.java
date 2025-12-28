package com.acc.franchise.repository;

import com.acc.franchise.domain.Franchise;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FranchiseRepository
        extends ReactiveCrudRepository<Franchise, UUID> {

    Mono<Boolean> existsByName(String name);

    @Query("""
        SELECT *
        FROM franchises
        ORDER BY name
        LIMIT :limit OFFSET :offset
    """)
    Flux<Franchise> findAllPaged(int limit, long offset);
}
