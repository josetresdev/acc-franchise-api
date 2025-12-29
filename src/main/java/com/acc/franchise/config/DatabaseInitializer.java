package com.acc.franchise.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(ConnectionFactory connectionFactory) {
        return args -> {
            DatabaseClient client = DatabaseClient.create(connectionFactory);

            Flux.concat(
                // Tabla de franquicias
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

                client.sql("""
                    CREATE INDEX IF NOT EXISTS idx_franchises_deleted_at
                    ON franchises (deleted_at);
                """).then(),

                // Tabla de sucursales
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

                client.sql("""
                    CREATE INDEX IF NOT EXISTS idx_franchise_branches_franchise_id
                    ON franchise_branches (franchise_id);
                """).then(),

                client.sql("""
                    CREATE INDEX IF NOT EXISTS idx_franchise_branches_deleted_at
                    ON franchise_branches (deleted_at);
                """).then(),

                // Tabla de productos
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

                client.sql("""
                    CREATE INDEX IF NOT EXISTS idx_products_franchise_branch_id
                    ON products (franchise_branch_id);
                """).then(),

                client.sql("""
                    CREATE INDEX IF NOT EXISTS idx_products_stock
                    ON products (stock);
                """).then(),

                client.sql("""
                    CREATE INDEX IF NOT EXISTS idx_products_deleted_at
                    ON products (deleted_at);
                """).then()
            ).subscribe();
        };
    }
}
