-- ---------- Base de datos ----------
CREATE DATABASE IF NOT EXISTS acc_franchise CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE acc_franchise;

-- ---------- Tabla franchises ----------
CREATE TABLE IF NOT EXISTS franchises (
    id CHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(120) NOT NULL UNIQUE
);

-- ---------- Tabla branches ----------
CREATE TABLE IF NOT EXISTS branches (
    id CHAR(36) NOT NULL PRIMARY KEY,
    franchise_id CHAR(36) NOT NULL,
    name VARCHAR(120) NOT NULL,
    CONSTRAINT fk_franchise FOREIGN KEY (franchise_id) REFERENCES franchises(id) ON DELETE CASCADE
);

-- ---------- Tabla products ----------
CREATE TABLE IF NOT EXISTS products (
    id CHAR(36) NOT NULL PRIMARY KEY,
    branch_id CHAR(36) NOT NULL,
    name VARCHAR(120) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE CASCADE
);
