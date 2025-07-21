package com.example.bookstore.config;

import com.example.bookstore.repository.InventoryRepository;
import com.example.bookstore.repository.CsvInventoryRepository;
import com.example.bookstore.repository.InMemoryInventoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryConfig {

    @Value("${inventory.repository.type:memory}")
    private String repositoryType;

    @Bean
    public InventoryRepository inventoryRepository() {
        switch (repositoryType.toLowerCase()) {
            case "csv":
                return new CsvInventoryRepository();
            case "memory":
                return new InMemoryInventoryRepository();
            default:
                throw new IllegalArgumentException(
                        "Invalid repository type: " + repositoryType +
                                ". Supported types are: 'csv', 'memory'");
        }
    }
}
