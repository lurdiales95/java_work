package com.inventory.manager.data;


// utilized for dependency interjection before constructor. Utilize SpringBoot.
// would be the interface.

// Have 5 basic methods : getAll, getByID, add, update, remove


public class CSVInventoryRepository {
}


// CSV inventory repository. Load from file. Buffer Reader.
// the txt can be transformed into the CSVInventoryRepository

// @Value and @PostConstructor


//
//package com.example.bookstore;
//
//import com.example.bookstore.model.InventoryItem;
//import com.example.bookstore.repository.CsvInventoryRepository;
//import com.example.bookstore.repository.InventoryRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.TestPropertySource;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = CSVInventoryTests.TestConfig.class)
//@TestPropertySource(properties = {
//        "inventory.csv.filepath=data/test/inventory-test.csv",
//        "inventory.csv.seedpath=data/test/inventory-test-seed.csv"
//})
//public class CSVInventoryTests {
//    @Autowired
//    private InventoryRepository inventoryRepository;
//
//    @Value("${inventory.csv.seedpath}")
//    private String seedPath;
//
//    @Value("${inventory.csv.filepath}")
//    private String testPath;
//
//    @BeforeEach
//    void setUp() throws IOException {
//        // Copy seed file to target location
//        Path seedFile = Paths.get(seedPath);
//        Path targetFile = Paths.get(testPath);
//
//        Files.copy(seedFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
//    }
//
//    @Test
//    void canLoadData() {
//        List<InventoryItem> items = inventoryRepository.getAll();
//        assertEquals(3, items.size());
//    }
//
//    @Configuration
//    static class TestConfig {
//        @Bean
//        public InventoryRepository inventoryRepository() {
//            return new CsvInventoryRepository();
//        }
//    }
//}