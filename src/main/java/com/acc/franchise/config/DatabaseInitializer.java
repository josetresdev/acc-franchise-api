package com.acc.franchise.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class DatabaseInitializer {

    /**
     * Initializes the database schema at application startup.
     * Creates tables for franchises, franchise branches, and products, along with necessary indexes.
     * Uses reactive DatabaseClient to execute SQL statements and handles errors gracefully.
     *
     * @param connectionFactory R2DBC ConnectionFactory
     * @return CommandLineRunner to execute on application startup
     */
    @Bean
    public CommandLineRunner initializeDatabase(ConnectionFactory connectionFactory) {
        return args -> {
            DatabaseClient client = DatabaseClient.create(connectionFactory);

            Flux.concat(
                // ===================== Franchises table =====================
                client.sql("""
                    CREATE TABLE IF NOT EXISTS franchises (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(120) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        CONSTRAINT pk_franchises PRIMARY KEY (id),
                        CONSTRAINT uq_franchises_name UNIQUE (name)
                    ) ENGINE=InnoDB;
                """).then(),

                // Index for soft-deleted franchises
                client.sql("CREATE INDEX idx_franchises_deleted_at ON franchises (deleted_at);")
                      .then()
                      .onErrorResume(e -> Mono.empty()), // ignore error if index exists

                // ===================== Franchise branches table =====================
                client.sql("""
                    CREATE TABLE IF NOT EXISTS franchise_branches (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        franchise_id BIGINT NOT NULL,
                        name VARCHAR(120) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        CONSTRAINT pk_franchise_branches PRIMARY KEY (id),
                        CONSTRAINT fk_franchise_branches_franchise
                            FOREIGN KEY (franchise_id)
                            REFERENCES franchises(id)
                            ON DELETE CASCADE
                    ) ENGINE=InnoDB;
                """).then(),

                // Index for franchise branches by franchise ID
                client.sql("CREATE INDEX idx_franchise_branches_franchise_id ON franchise_branches (franchise_id);")
                      .then().onErrorResume(e -> Mono.empty()),

                // Index for soft-deleted branches
                client.sql("CREATE INDEX idx_franchise_branches_deleted_at ON franchise_branches (deleted_at);")
                      .then().onErrorResume(e -> Mono.empty()),

                // ===================== Products table =====================
                client.sql("""
                    CREATE TABLE IF NOT EXISTS products (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        franchise_branch_id BIGINT NOT NULL,
                        name VARCHAR(120) NOT NULL,
                        stock INT NOT NULL DEFAULT 0,
                        price DECIMAL(10,2) NOT NULL DEFAULT 0.0,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                        deleted_at TIMESTAMP NULL DEFAULT NULL,
                        CONSTRAINT pk_products PRIMARY KEY (id),
                        CONSTRAINT fk_products_franchise_branch
                            FOREIGN KEY (franchise_branch_id)
                            REFERENCES franchise_branches(id)
                            ON DELETE CASCADE
                    ) ENGINE=InnoDB;
                """).then(),

                // Index for products by branch ID
                client.sql("CREATE INDEX idx_products_franchise_branch_id ON products (franchise_branch_id);")
                      .then().onErrorResume(e -> Mono.empty()),

                // Index for products by stock
                client.sql("CREATE INDEX idx_products_stock ON products (stock);")
                      .then().onErrorResume(e -> Mono.empty()),

                // Index for soft-deleted products
                client.sql("CREATE INDEX idx_products_deleted_at ON products (deleted_at);")
                      .then().onErrorResume(e -> Mono.empty())
            ).subscribe(); // Execute all statements reactively
        };
    }
}
