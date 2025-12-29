package com.acc.franchise.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Table("franchises")
public class Franchise {

    @Id
    private UUID id;

    @Column("uid")
    private UUID uid;

    @Column("name")
    private String name;

    protected Franchise() {
        // Required by R2DBC
    }

    private Franchise(UUID id, UUID uid, String name) {
        this.id = id;
        this.uid = uid;
        this.name = name;
    }

    /**
     * Factory method to create a new Franchise.
     */
    public static Franchise create(String name) {
        return new Franchise(
                UUID.randomUUID(), // internal id
                UUID.randomUUID(), // public uid
                name
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Franchise)) return false;
        Franchise that = (Franchise) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
