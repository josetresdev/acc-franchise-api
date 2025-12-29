package com.acc.franchise.repository;

import com.acc.franchise.domain.Franchise;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FranchiseRepository
        extends ReactiveCrudRepository<Franchise, UUID> {

    /**
     * Checks if a franchise with the given name exists and is not soft deleted.
     */
    @Query("""
        SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
        FROM franchises
        WHERE name = :name
          AND deleted_at IS NULL
    """)
    Mono<Boolean> existsByName(String name);

    /**
     * Finds a franchise by public UID.
     */
    @Query("""
        SELECT *
        FROM franchises
        WHERE uid = :uid
          AND deleted_at IS NULL
        LIMIT 1
    """)
    Mono<Franchise> findByUid(UUID uid);

    /**
     * Returns a paginated list of franchises (soft deletes excluded).
     */
    @Query("""
        SELECT *
        FROM franchises
        WHERE deleted_at IS NULL
        ORDER BY name ASC
        LIMIT :limit OFFSET :offset
    """)
    Flux<Franchise> findAllPaged(int limit, long offset);
}
