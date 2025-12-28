package com.acc.franchise.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(ConnectionFactory connectionFactory) {
        return args -> {
            DatabaseClient client = DatabaseClient.create(connectionFactory);

            client.sql("""
                CREATE TABLE IF NOT EXISTS franchises (
                    id CHAR(36) NOT NULL PRIMARY KEY,
                    name VARCHAR(120) NOT NULL UNIQUE
                );
                CREATE TABLE IF NOT EXISTS branches (
                    id CHAR(36) NOT NULL PRIMARY KEY,
                    franchise_id CHAR(36) NOT NULL,
                    name VARCHAR(120) NOT NULL,
                    CONSTRAINT fk_franchise FOREIGN KEY (franchise_id) REFERENCES franchises(id) ON DELETE CASCADE
                );
                CREATE TABLE IF NOT EXISTS products (
                    id CHAR(36) NOT NULL PRIMARY KEY,
                    branch_id CHAR(36) NOT NULL,
                    name VARCHAR(120) NOT NULL,
                    price DECIMAL(10,2) NOT NULL,
                    CONSTRAINT fk_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE CASCADE
                );
            """).fetch().rowsUpdated().subscribe();
        };
    }
}
