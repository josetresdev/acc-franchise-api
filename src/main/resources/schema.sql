-- =========================================================
-- Database
-- =========================================================
CREATE DATABASE IF NOT EXISTS acc_franchise
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE acc_franchise;

-- =========================================================
-- Table: franchises
-- Context: a franchise is the aggregate root
-- =========================================================
CREATE TABLE IF NOT EXISTS franchises (
    id CHAR(36) NOT NULL,
    uid CHAR(36) NOT NULL,
    name VARCHAR(120) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT pk_franchises PRIMARY KEY (id),
    CONSTRAINT uq_franchises_uid UNIQUE (uid),
    CONSTRAINT uq_franchises_name UNIQUE (name)
) ENGINE=InnoDB;

CREATE INDEX idx_franchises_deleted_at
    ON franchises (deleted_at);

-- =========================================================
-- Table: franchise_branches
-- Context: a franchise is composed of many branches
-- =========================================================
CREATE TABLE IF NOT EXISTS franchise_branches (
    id CHAR(36) NOT NULL,
    uid CHAR(36) NOT NULL,
    franchise_id CHAR(36) NOT NULL,
    name VARCHAR(120) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT pk_franchise_branches PRIMARY KEY (id),
    CONSTRAINT uq_franchise_branches_uid UNIQUE (uid),
    CONSTRAINT fk_franchise_branches_franchise
        FOREIGN KEY (franchise_id)
        REFERENCES franchises(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_franchise_branches_franchise_id
    ON franchise_branches (franchise_id);

CREATE INDEX idx_franchise_branches_deleted_at
    ON franchise_branches (deleted_at);

-- =========================================================
-- Table: products
-- Context: a branch offers many products
-- =========================================================
CREATE TABLE IF NOT EXISTS products (
    id CHAR(36) NOT NULL,
    uid CHAR(36) NOT NULL,
    franchise_branch_id CHAR(36) NOT NULL,
    name VARCHAR(120) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL DEFAULT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id),
    CONSTRAINT uq_products_uid UNIQUE (uid),
    CONSTRAINT fk_products_franchise_branch
        FOREIGN KEY (franchise_branch_id)
        REFERENCES franchise_branches(id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_products_franchise_branch_id
    ON products (franchise_branch_id);

CREATE INDEX idx_products_stock
    ON products (stock);

CREATE INDEX idx_products_deleted_at
    ON products (deleted_at);

-- =========================================================
-- Notes
-- =========================================================
-- id  : internal technical UUID (PK)
-- uid : public UUID exposed in API contracts
-- deleted_at implements soft delete strategy
-- All application queries should filter WHERE deleted_at IS NULL
-- UUIDs must be generated at application level
