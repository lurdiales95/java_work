package org.example.data.impl;

import org.example.data.exceptions.InternalErrorException;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.repository.ItemRepo;
import org.example.model.Item;
import org.example.model.ItemCategory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class MySQLItemRepoTest {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private int testCategoryId;
    private int testItemId;
    private int expiredItemId;

    @BeforeEach
    void setUp() {
        setupTestData();
    }

    @AfterEach
    void cleanup() {
        cleanupTestData();
    }

    private void setupTestData() {
        try {
            // Clean up any existing test data
            cleanupTestData();

            // Create test category
            jdbcTemplate.update("INSERT INTO ItemCategory (ItemCategoryName) VALUES (?)", "Test Category");
            testCategoryId = jdbcTemplate.queryForObject(
                    "SELECT ItemCategoryID FROM ItemCategory WHERE ItemCategoryName = ?",
                    Integer.class, "Test Category"
            );

            // Create test item (currently available)
            LocalDate startDate = LocalDate.now().minusDays(10);
            jdbcTemplate.update(
                    "INSERT INTO Item (ItemCategoryID, ItemName, ItemDescription, StartDate, EndDate, UnitPrice) VALUES (?, ?, ?, ?, ?, ?)",
                    testCategoryId, "Test Burger", "A delicious test burger", startDate, null, new BigDecimal("12.99")
            );
            testItemId = jdbcTemplate.queryForObject(
                    "SELECT ItemID FROM Item WHERE ItemName = ?",
                    Integer.class, "Test Burger"
            );

            // Create expired item (no longer available)
            LocalDate expiredStart = LocalDate.now().minusDays(30);
            LocalDate expiredEnd = LocalDate.now().minusDays(5);
            jdbcTemplate.update(
                    "INSERT INTO Item (ItemCategoryID, ItemName, ItemDescription, StartDate, EndDate, UnitPrice) VALUES (?, ?, ?, ?, ?, ?)",
                    testCategoryId, "Expired Pizza", "An expired test pizza", expiredStart, expiredEnd, new BigDecimal("15.99")
            );
            expiredItemId = jdbcTemplate.queryForObject(
                    "SELECT ItemID FROM Item WHERE ItemName = ?",
                    Integer.class, "Expired Pizza"
            );

        } catch (Exception e) {
            System.out.println("Warning: Could not set up test data - " + e.getMessage());
        }
    }

    private void cleanupTestData() {
        try {
            jdbcTemplate.update("DELETE FROM Item WHERE ItemName IN (?, ?)", "Test Burger", "Expired Pizza");
            jdbcTemplate.update("DELETE FROM ItemCategory WHERE ItemCategoryName = ?", "Test Category");
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    @Test
    void getItemById_ShouldReturnValidItem() throws RecordNotFoundException, InternalErrorException {
        // Act
        Item item = itemRepo.getItemById(testItemId);

        // Assert
        assertThat(item).isNotNull();
        assertThat(item.getItemID()).isEqualTo(testItemId);
        assertThat(item.getItemName()).isEqualTo("Test Burger");
        assertThat(item.getItemDescription()).isEqualTo("A delicious test burger");
        assertThat(item.getItemCategoryID()).isEqualTo(testCategoryId);
        assertThat(item.getUnitPrice()).isEqualByComparingTo(new BigDecimal("12.99"));
        assertThat(item.getStartDate()).isNotNull();
        assertThat(item.getEndDate()).isNull(); // Active item has no end date
    }

    @Test
    void getItemById_ShouldReturnExpiredItem() throws RecordNotFoundException, InternalErrorException {
        // Even expired items should be retrievable by ID
        Item item = itemRepo.getItemById(expiredItemId);

        assertThat(item).isNotNull();
        assertThat(item.getItemID()).isEqualTo(expiredItemId);
        assertThat(item.getItemName()).isEqualTo("Expired Pizza");
        assertThat(item.getEndDate()).isNotNull(); // Expired item has end date
        assertThat(item.getEndDate()).isBefore(LocalDate.now());
    }

    @Test
    void getItemById_ShouldThrowExceptionForInvalidId() {
        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            itemRepo.getItemById(999999);
        });
    }

    @Test
    void getAllAvailableItems_ShouldReturnCurrentItems() throws InternalErrorException {
        // Arrange
        LocalDate today = LocalDate.now();

        // Act
        List<Item> items = itemRepo.getAllAvailableItems(today);

        // Assert
        assertThat(items).isNotNull();

        // Should contain our active test item
        boolean foundTestItem = items.stream()
                .anyMatch(item -> item.getItemID() == testItemId);
        assertThat(foundTestItem).isTrue();

        // Should NOT contain our expired item
        boolean foundExpiredItem = items.stream()
                .anyMatch(item -> item.getItemID() == expiredItemId);
        assertThat(foundExpiredItem).isFalse();

        // Validate all returned items are available for the given date
        for (Item item : items) {
            assertThat(item.getStartDate()).isBeforeOrEqualTo(today);
            if (item.getEndDate() != null) {
                assertThat(item.getEndDate()).isAfterOrEqualTo(today);
            }
            assertThat(item.getUnitPrice()).isPositive();
        }
    }

    @Test
    void getAllAvailableItems_ShouldReturnEmptyForPastDate() throws InternalErrorException {
        // Arrange - date before any of our test items started
        LocalDate pastDate = LocalDate.now().minusDays(60);

        // Act
        List<Item> items = itemRepo.getAllAvailableItems(pastDate);

        // Assert
        assertThat(items).isNotNull();

        // Should not contain our test items (they started more recently)
        boolean foundTestItem = items.stream()
                .anyMatch(item -> item.getItemID() == testItemId || item.getItemID() == expiredItemId);
        assertThat(foundTestItem).isFalse();
    }

    @Test
    void getAllAvailableItems_ShouldIncludeExpiredItemForValidDate() throws InternalErrorException {
        // Arrange - date when the expired item was still valid
        LocalDate pastValidDate = LocalDate.now().minusDays(15);

        // Act
        List<Item> items = itemRepo.getAllAvailableItems(pastValidDate);

        // Assert
        assertThat(items).isNotNull();

        // Should contain both items (both were valid 15 days ago)
        boolean foundTestItem = items.stream()
                .anyMatch(item -> item.getItemID() == testItemId);
        boolean foundExpiredItem = items.stream()
                .anyMatch(item -> item.getItemID() == expiredItemId);

        assertThat(foundTestItem).isTrue();
        assertThat(foundExpiredItem).isTrue();
    }

    @Test
    void getItemsByCategory_ShouldReturnItemsInCategory() throws InternalErrorException {
        // Arrange
        LocalDate today = LocalDate.now();

        // Act
        List<Item> items = itemRepo.getItemsByCategory(today, testCategoryId);

        // Assert
        assertThat(items).isNotNull();

        // Should contain our active test item
        boolean foundTestItem = items.stream()
                .anyMatch(item -> item.getItemID() == testItemId);
        assertThat(foundTestItem).isTrue();

        // Should NOT contain expired item
        boolean foundExpiredItem = items.stream()
                .anyMatch(item -> item.getItemID() == expiredItemId);
        assertThat(foundExpiredItem).isFalse();

        // All items should be in the specified category
        for (Item item : items) {
            assertThat(item.getItemCategoryID()).isEqualTo(testCategoryId);
            assertThat(item.getStartDate()).isBeforeOrEqualTo(today);
            if (item.getEndDate() != null) {
                assertThat(item.getEndDate()).isAfterOrEqualTo(today);
            }
        }
    }

    @Test
    void getItemsByCategory_ShouldReturnEmptyForInvalidCategory() throws InternalErrorException {
        // Arrange
        LocalDate today = LocalDate.now();
        int invalidCategoryId = 999999;

        // Act
        List<Item> items = itemRepo.getItemsByCategory(today, invalidCategoryId);

        // Assert
        assertThat(items).isNotNull();
        assertThat(items).isEmpty();
    }

    @Test
    void getItemsByCategory_ShouldRespectDateFiltering() throws InternalErrorException {
        // Arrange - date when expired item was still valid
        LocalDate pastDate = LocalDate.now().minusDays(15);

        // Act
        List<Item> items = itemRepo.getItemsByCategory(pastDate, testCategoryId);

        // Assert
        assertThat(items).isNotNull();

        // Should contain both items (both were valid on this date)
        assertThat(items).hasSize(2);

        boolean foundTestItem = items.stream()
                .anyMatch(item -> item.getItemID() == testItemId);
        boolean foundExpiredItem = items.stream()
                .anyMatch(item -> item.getItemID() == expiredItemId);

        assertThat(foundTestItem).isTrue();
        assertThat(foundExpiredItem).isTrue();
    }

    @Test
    void getAllItemCategories_ShouldReturnAllCategories() throws InternalErrorException {
        // Act
        List<ItemCategory> categories = itemRepo.getAllItemCategories();

        // Assert
        assertThat(categories).isNotNull();
        assertThat(categories).isNotEmpty();

        // Should contain our test category
        boolean foundTestCategory = categories.stream()
                .anyMatch(cat -> cat.getItemCategoryID() == testCategoryId);
        assertThat(foundTestCategory).isTrue();

        // Validate all categories have required fields
        for (ItemCategory category : categories) {
            assertThat(category.getItemCategoryID()).isPositive();
            assertThat(category.getItemCategoryName()).isNotNull();
            assertThat(category.getItemCategoryName().trim()).isNotEmpty();
        }

        // Find our test category and validate it
        ItemCategory testCategory = categories.stream()
                .filter(cat -> cat.getItemCategoryID() == testCategoryId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Test category not found"));

        assertThat(testCategory.getItemCategoryName()).isEqualTo("Test Category");
    }

    @Test
    void getAllItemCategories_ShouldBeSortedByName() throws InternalErrorException {
        // Act
        List<ItemCategory> categories = itemRepo.getAllItemCategories();

        // Assert
        assertThat(categories).isNotNull();

        if (categories.size() > 1) {
            // Verify categories are sorted by name
            assertThat(categories).isSortedAccordingTo((cat1, cat2) ->
                    cat1.getItemCategoryName().compareToIgnoreCase(cat2.getItemCategoryName()));
        }
    }

    @Test
    void repository_ShouldHandleEdgeCases() throws InternalErrorException, RecordNotFoundException {
        // Test with exact start date
        LocalDate exactStartDate = LocalDate.now().minusDays(10);
        List<Item> itemsOnStartDate = itemRepo.getAllAvailableItems(exactStartDate);

        boolean foundTestItemOnStartDate = itemsOnStartDate.stream()
                .anyMatch(item -> item.getItemID() == testItemId);
        assertThat(foundTestItemOnStartDate).isTrue();

        // Test with day before start date
        LocalDate beforeStartDate = exactStartDate.minusDays(1);
        List<Item> itemsBeforeStart = itemRepo.getAllAvailableItems(beforeStartDate);

        boolean foundTestItemBeforeStart = itemsBeforeStart.stream()
                .anyMatch(item -> item.getItemID() == testItemId);
        assertThat(foundTestItemBeforeStart).isFalse();
    }
}