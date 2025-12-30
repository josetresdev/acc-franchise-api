package com.acc.franchise.repository;

import com.acc.franchise.domain.FranchiseBranch;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseBranchRepository extends ReactiveCrudRepository<FranchiseBranch, Long> {

    /**
     * Counts active branches with a specific name within a franchise.
     * Used to prevent duplicate branch names in the same franchise.
     *
     * @param name        the branch name to check
     * @param franchiseId the ID of the franchise
     * @return Mono containing the count of matching branches
     */
    @Query("SELECT COUNT(*) FROM franchise_branches WHERE name = :name AND franchise_id = :franchiseId AND deleted_at IS NULL")
    Mono<Long> countByNameAndFranchise(String name, Long franchiseId);

    /**
     * Retrieves a paginated list of active branches for a given franchise.
     * Only considers branches that are not soft-deleted (deleted_at IS NULL).
     *
     * @param franchiseId the ID of the franchise
     * @param limit       maximum number of records to return
     * @param offset      offset for pagination
     * @return Flux stream of FranchiseBranch entities
     */
    @Query("SELECT * FROM franchise_branches WHERE franchise_id = :franchiseId AND deleted_at IS NULL ORDER BY name ASC LIMIT :limit OFFSET :offset")
    Flux<FranchiseBranch> findAllPagedByFranchise(Long franchiseId, int limit, long offset);

    /**
     * Counts all active branches within a specific franchise.
     * Only considers branches that are not soft-deleted.
     *
     * @param franchiseId the ID of the franchise
     * @return Mono containing the count of active branches
     */
    @Query("SELECT COUNT(*) FROM franchise_branches WHERE franchise_id = :franchiseId AND deleted_at IS NULL")
    Mono<Long> countAllActiveByFranchise(Long franchiseId);
}
