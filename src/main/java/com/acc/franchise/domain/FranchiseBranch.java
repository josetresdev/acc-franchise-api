package com.acc.franchise.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Table("franchise_branches")
public class FranchiseBranch {

    @Id
    @Column("id")
    private Long id;

    @Column("franchise_id")
    private Long franchiseId;

    @Column("name")
    private String name;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

    public FranchiseBranch() {} // required by R2DBC

    public FranchiseBranch(Long franchiseId, String name) {
        this.franchiseId = franchiseId;
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() { return id; }
    public Long getFranchiseId() { return franchiseId; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }

    public void setId(Long id) { this.id = id; }
    public void setFranchiseId(Long franchiseId) { this.franchiseId = franchiseId; }
    public void setName(String name) { this.name = name; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
