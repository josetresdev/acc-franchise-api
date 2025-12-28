package com.acc.franchise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "franchises")
public class Franchise {

    @Id
    @UuidGenerator
    @Column(
        name = "id",
        columnDefinition = "BINARY(16)",
        nullable = false,
        updatable = false
    )
    private UUID id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    protected Franchise() {
        // Required by JPA
    }

    public Franchise(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
