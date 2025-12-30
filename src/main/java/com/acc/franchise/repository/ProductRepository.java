package com.acc.franchise.repository;

import com.acc.franchise.domain.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    @Query("""
        SELECT COUNT(*) 
        FROM products
        WHERE name = :name
          AND franchise_branch_id = :branchId
          AND deleted_at IS NULL
    """)
    Mono<Long> countByNameAndBranch(String name, Long branchId);

    @Query("""
        SELECT COUNT(*) 
        FROM products
        WHERE franchise_branch_id = :branchId
          AND deleted_at IS NULL
    """)
    Mono<Long> countAllActiveByBranch(Long branchId);

    @Query("""
        SELECT *
        FROM products
        WHERE franchise_branch_id = :branchId
          AND deleted_at IS NULL
        ORDER BY name ASC
        LIMIT :limit OFFSET :offset
    """)
    Flux<Product> findAllPagedByBranch(Long branchId, int limit, long offset);

    @Query("""
        SELECT *
        FROM products
        WHERE id = :id
          AND deleted_at IS NULL
    """)
    Mono<Product> findActiveById(Long id);

    @Query("""
        UPDATE products
        SET deleted_at = CURRENT_TIMESTAMP
        WHERE id = :id AND deleted_at IS NULL
    """)
    Mono<Integer> softDelete(Long id);

    @Query("""
        SELECT *
        FROM products
        WHERE franchise_branch_id = :branchId
          AND deleted_at IS NULL
    """)
    Flux<Product> findAllByBranch(Long branchId);

    @Query("""
        SELECT p.*, b.name AS branch_name
        FROM products p
        JOIN franchise_branches b ON p.franchise_branch_id = b.id
        WHERE b.franchise_id = :franchiseId
          AND p.deleted_at IS NULL
          AND p.stock = (
              SELECT MAX(stock)
              FROM products p2
              JOIN franchise_branches b2 ON p2.franchise_branch_id = b2.id
              WHERE b2.franchise_id = :franchiseId AND p2.deleted_at IS NULL
          )
    """)
    Flux<Product> findMaxStockByFranchise(Long franchiseId);
}
