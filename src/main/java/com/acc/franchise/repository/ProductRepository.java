package com.acc.franchise.repository;

import com.acc.franchise.domain.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    /**
     * Counts active products with a specific name within a branch.
     * Used to prevent duplicate product names in the same branch.
     *
     * @param name     the product name to check
     * @param branchId the ID of the franchise branch
     * @return Mono containing the count of matching products
     */
    @Query("""
        SELECT COUNT(*) 
        FROM products
        WHERE name = :name
          AND franchise_branch_id = :branchId
          AND deleted_at IS NULL
    """)
    Mono<Long> countByNameAndBranch(String name, Long branchId);

    /**
     * Counts all active products within a specific branch.
     * Only considers products that are not soft-deleted.
     *
     * @param branchId the ID of the franchise branch
     * @return Mono containing the count of active products
     */
    @Query("""
        SELECT COUNT(*) 
        FROM products
        WHERE franchise_branch_id = :branchId
          AND deleted_at IS NULL
    """)
    Mono<Long> countAllActiveByBranch(Long branchId);

    /**
     * Retrieves a paginated list of active products for a given branch.
     * Only considers products that are not soft-deleted.
     *
     * @param branchId the ID of the franchise branch
     * @param limit    maximum number of records to return
     * @param offset   offset for pagination
     * @return Flux stream of Product entities
     */
    @Query("""
        SELECT *
        FROM products
        WHERE franchise_branch_id = :branchId
          AND deleted_at IS NULL
        ORDER BY name ASC
        LIMIT :limit OFFSET :offset
    """)
    Flux<Product> findAllPagedByBranch(Long branchId, int limit, long offset);

    /**
     * Retrieves a single active product by ID.
     * Ensures the product has not been soft-deleted.
     *
     * @param id the product ID
     * @return Mono of Product
     */
    @Query("""
        SELECT *
        FROM products
        WHERE id = :id
          AND deleted_at IS NULL
    """)
    Mono<Product> findActiveById(Long id);

    /**
     * Soft deletes a product by setting its deleted_at timestamp.
     * Ensures only non-deleted products are affected.
     *
     * @param id the product ID to soft delete
     * @return Mono containing number of affected rows
     */
    @Query("""
        UPDATE products
        SET deleted_at = CURRENT_TIMESTAMP
        WHERE id = :id AND deleted_at IS NULL
    """)
    Mono<Integer> softDelete(Long id);

    /**
     * Retrieves all active products for a specific branch.
     * Only considers products that are not soft-deleted.
     *
     * @param branchId the ID of the franchise branch
     * @return Flux stream of Product entities
     */
    @Query("""
        SELECT *
        FROM products
        WHERE franchise_branch_id = :branchId
          AND deleted_at IS NULL
    """)
    Flux<Product> findAllByBranch(Long branchId);

    /**
     * Retrieves products with the maximum stock per franchise.
     * Joins products with branches to filter by franchise and ensures only active products are considered.
     *
     * @param franchiseId the ID of the franchise
     * @return Flux stream of Product entities with maximum stock
     */
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
