package com.acc.franchise.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class FranchiseBranch {

    private String id;
    private String uid;
    private String franchiseId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private FranchiseBranch(String id, String uid, String franchiseId, String name) {
        this.id = id;
        this.uid = uid;
        this.franchiseId = franchiseId;
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    // Factory method
    public static FranchiseBranch create(String franchiseId, String name) {
        return new FranchiseBranch(UUID.randomUUID().toString(), UUID.randomUUID().toString(), franchiseId, name);
    }

    // Getters and setters
    public String getId() { return id; }
    public String getUid() { return uid; }
    public String getFranchiseId() { return franchiseId; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }

    public void setName(String name) { this.name = name; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
