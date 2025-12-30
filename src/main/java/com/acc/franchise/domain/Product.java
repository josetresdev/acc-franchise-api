package com.acc.franchise.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("products")
public class Product {

    @Id
    private Long id;

    @Column("franchise_branch_id")
    private Long franchiseBranchId;

    @Column("name")
    private String name;

    @Column("stock")
    private int stock;

    @Column("price")
    private BigDecimal price;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

    public Product() {}

    public Product(Long franchiseBranchId, String name, int stock, BigDecimal price) {
        this.franchiseBranchId = franchiseBranchId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFranchiseBranchId() { return franchiseBranchId; }
    public void setFranchiseBranchId(Long franchiseBranchId) { this.franchiseBranchId = franchiseBranchId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
