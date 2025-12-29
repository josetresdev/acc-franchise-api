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
     * Cuenta las franquicias activas con un nombre específico (para evitar duplicados)
     */
    @Query("""
        SELECT COUNT(*) 
        FROM franchises
        WHERE name = :name
          AND deleted_at IS NULL
    """)
    Mono<Integer> countByNameAndNotDeleted(String name);

    /**
     * Obtiene un listado de franquicias activas paginadas
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
     * Crear una franquicia (insert)
     * ReactiveCrudRepository ya provee: save(Franchise)
     * Ejemplo de uso en Service:
     *    repository.save(Franchise.create("nombre"))
     */
}
