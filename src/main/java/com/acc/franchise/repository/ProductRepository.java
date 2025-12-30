package com.acc.franchise.repository;

import com.acc.franchise.domain.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

    @Query("""
        SELECT COUNT(*) 
        FROM products
        WHERE name = :name
          AND franchise_branch_id = :branchId
          AND deleted_at IS NULL
    """)
    Mono<Integer> countByNameAndBranch(String name, String branchId);

    @Query("""
        SELECT COUNT(*) 
        FROM products
        WHERE franchise_branch_id = :branchId
          AND deleted_at IS NULL
    """)
    Mono<Long> countAllActiveByBranch(String branchId);

    @Query("""
        SELECT *
        FROM products
        WHERE franchise_branch_id = :branchId
          AND deleted_at IS NULL
        ORDER BY name ASC
        LIMIT :limit OFFSET :offset
    """)
    Flux<Product> findAllPagedByBranch(String branchId, int limit, long offset);
}
