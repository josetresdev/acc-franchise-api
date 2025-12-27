package com.acc.franchise.repository;

import com.acc.franchise.domain.Franchise;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, UUID> {

    Optional<Franchise> findByName(String name);

    boolean existsByName(String name);
}
