package com.acc.franchise.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Product {

    private String id;
    private String uid;
    private String franchiseBranchId;
    private String name;
    private int stock;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private Product(String id, String uid, String franchiseBranchId, String name, int stock, BigDecimal price) {
        this.id = id;
        this.uid = uid;
        this.franchiseBranchId = franchiseBranchId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    // Factory method
    public static Product create(String franchiseBranchId, String name, int stock, BigDecimal price) {
        return new Product(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                franchiseBranchId,
                name,
                stock,
                price
        );
    }

    // Getters
    public String getId() { return id; }
    public String getUid() { return uid; }
    public String getFranchiseBranchId() { return franchiseBranchId; }
    public String getName() { return name; }
    public int getStock() { return stock; }
    public BigDecimal getPrice() { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
