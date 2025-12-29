package com.acc.franchise.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("franchises")
public class Franchise {

    @Id
    @Column("id")
    private Long id; // Autoincrement numérico en la base de datos

    @Column("name")
    private String name;

    @Column("deleted_at")
    private String deletedAt; // Opcional, para soft delete

    protected Franchise() {
        // Required by R2DBC
    }

    private Franchise(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Factory method para crear una nueva franquicia.
     * ID null => R2DBC hará INSERT y la base de datos generará autoincrement.
     */
    public static Franchise create(String name) {
        return new Franchise(null, name);
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void rename(String newName) { this.name = newName; }

    public String getDeletedAt() { return deletedAt; }
    public void setDeletedAt(String deletedAt) { this.deletedAt = deletedAt; }

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
