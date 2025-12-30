package com.acc.franchise.repository;

import com.acc.franchise.domain.Franchise;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseRepository extends ReactiveCrudRepository<Franchise, Long> {

    /**
     * Counts active franchises with a specific name to prevent duplicates.
     * Only considers records that are not soft-deleted (deleted_at IS NULL).
     *
     * @param name the franchise name to check
     * @return Mono containing the count of matching franchises
     */
    @Query("""
        SELECT COUNT(*) 
        FROM franchises
        WHERE name = :name
          AND deleted_at IS NULL
    """)
    Mono<Integer> countByNameAndNotDeleted(String name);

    /**
     * Retrieves a paginated list of active franchises.
     * Only considers records that are not soft-deleted (deleted_at IS NULL).
     *
     * @param limit  maximum number of records to return
     * @param offset offset for pagination
     * @return Flux stream of Franchise entities
     */
    @Query("""
        SELECT *
        FROM franchises
        WHERE deleted_at IS NULL
        ORDER BY name ASC
        LIMIT :limit OFFSET :offset
    """)
    Flux<Franchise> findAllPaged(int limit, long offset);

    /**
     * Create a new franchise (insert).
     * Note: ReactiveCrudRepository already provides the save(Franchise) method.
     * Example usage in Service:
     *    repository.save(Franchise.create("name"))
     */
}
