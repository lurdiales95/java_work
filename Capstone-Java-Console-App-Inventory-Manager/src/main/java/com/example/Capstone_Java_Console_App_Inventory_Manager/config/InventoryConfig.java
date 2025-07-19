package com.example.Capstone_Java_Console_App_Inventory_Manager.config;

import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.CsvInventoryRepository;
import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.InMemoryInventoryRepository;
import com.example.Capstone_Java_Console_App_Inventory_Manager.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryConfig {

    @Value("${candleinventory.respository.type:memory}")
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
