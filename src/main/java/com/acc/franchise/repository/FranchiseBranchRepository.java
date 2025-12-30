package com.acc.franchise.repository;

import com.acc.franchise.domain.FranchiseBranch;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseBranchRepository extends ReactiveCrudRepository<FranchiseBranch, Long> {

    @Query("SELECT COUNT(*) FROM franchise_branches WHERE name = :name AND franchise_id = :franchiseId AND deleted_at IS NULL")
    Mono<Long> countByNameAndFranchise(String name, String franchiseId);

    @Query("SELECT * FROM franchise_branches WHERE franchise_id = :franchiseId AND deleted_at IS NULL ORDER BY name ASC LIMIT :limit OFFSET :offset")
    Flux<FranchiseBranch> findAllPagedByFranchise(String franchiseId, int limit, long offset);

    @Query("SELECT COUNT(*) FROM franchise_branches WHERE franchise_id = :franchiseId AND deleted_at IS NULL")
    Mono<Long> countAllActiveByFranchise(String franchiseId);
}
