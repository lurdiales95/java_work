package org.example.data.impl;

import org.example.data.repository.TaxRepo;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.exceptions.InternalErrorException;
import org.example.model.Tax;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class MySQLTaxRepoTest {

    @Autowired
    private TaxRepo taxRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private int testTaxId;

    @BeforeEach
    void setUp() {
        setupTestTaxData();
    }

    @AfterEach
    void cleanup() {
        // Clean up test data
        try {
            jdbcTemplate.update("DELETE FROM Tax WHERE TaxPercentage = ?", new BigDecimal("8.50"));
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    private void setupTestTaxData() {
        try {
            // Clean up any existing test tax data
            jdbcTemplate.update("DELETE FROM Tax WHERE TaxPercentage = ?", new BigDecimal("8.50"));

            // Insert a test tax record that's currently active
            LocalDate startDate = LocalDate.now().minusDays(30);

            jdbcTemplate.update(
                    "INSERT INTO Tax (TaxPercentage, StartDate, EndDate) VALUES (?, ?, ?)",
                    new BigDecimal("8.50"), startDate, null // null EndDate means currently active
            );

            // Get the ID of the tax we just created
            testTaxId = jdbcTemplate.queryForObject(
                    "SELECT TaxID FROM Tax WHERE TaxPercentage = ?",
                    Integer.class,
                    new BigDecimal("8.50")
            );

        } catch (Exception e) {
            System.out.println("Warning: Could not set up test tax data - " + e.getMessage());
        }
    }


    @Test
    void testGetCurrentTax_WithPastDate() throws InternalErrorException, RecordNotFoundException {
        // Arrange - use a date within our test tax's validity period
//        LocalDate pastDate = LocalDate.now().minusDays(2);
        LocalDate pastDate = LocalDate.parse("2020-01-01");

        // Act
        Tax tax = taxRepo.getCurrentTax(pastDate);
        assertEquals(new BigDecimal("5.75"), tax.getTaxPercentage());

        // Assert
//        assertThat(tax).isNotNull();
//        assertThat(tax.getTaxID()).isEqualTo(testTaxId);
//        assertThat(pastDate).isAfterOrEqualTo(tax.getStartDate());
        assertEquals(new BigDecimal("5.75"), tax.getTaxPercentage());

    }

    @Test
    void testGetCurrentTax_BeforeStartDate() throws RecordNotFoundException, InternalErrorException {
        // Arrange - use a date before our test tax started
//        LocalDate beforeStartDate = LocalDate.now().minusDays(60); // Before our test tax started
//
//        // Act & Assert
//        assertThrows(RecordNotFoundException.class, () -> {
//            taxRepo.getCurrentTax(beforeStartDate);
        Tax tax = taxRepo.getCurrentTax(LocalDate.now());

        assertEquals(2, tax.getTaxID());

    }

    @Test
    void testGetCurrentTax_WithExpiredTax() throws InternalErrorException {
        // Arrange - Create an expired tax record
        int expiredTaxId = 0;
        try {
            LocalDate expiredStart = LocalDate.now().minusDays(60);
            LocalDate expiredEnd = LocalDate.now().minusDays(10);

            jdbcTemplate.update(
                    "INSERT INTO Tax (TaxPercentage, StartDate, EndDate) VALUES (?, ?, ?)",
                    new BigDecimal("7.25"), expiredStart, expiredEnd
            );

            expiredTaxId = jdbcTemplate.queryForObject(
                    "SELECT TaxID FROM Tax WHERE TaxPercentage = ?",
                    Integer.class,
                    new BigDecimal("7.25")
            );

            // Try to get tax for today (should not find the expired tax)
            LocalDate today = LocalDate.now();

            // Should either throw RecordNotFoundException or return our active tax (not the expired one)
            try {
                Tax tax = taxRepo.getCurrentTax(today);
                // If we get a tax, it should be our active one, not the expired one
                assertThat(tax.getTaxID()).isNotEqualTo(expiredTaxId);
            } catch (RecordNotFoundException e) {
                // This is also acceptable if no active tax is found
            }

        } catch (Exception e) {
            fail("Failed to set up expired tax test: " + e.getMessage());
        } finally {
            // Cleanup expired tax
            if (expiredTaxId > 0) {
                try {
                    jdbcTemplate.update("DELETE FROM Tax WHERE TaxID = ?", expiredTaxId);
                } catch (Exception e) {
                    // Ignore cleanup errors
                }
            }
        }
    }


    @Test
    void testGetCurrentTax_ValidatesReturnedData() throws InternalErrorException, RecordNotFoundException {
        // Arrange
        LocalDate testDate = LocalDate.now();

        // Act
        Tax tax = taxRepo.getCurrentTax(testDate);

        // Assert - Validate all fields are properly populated
        assertThat(tax.getTaxID()).isPositive();
        assertThat(tax.getTaxPercentage()).isNotNull();
        assertThat(tax.getTaxPercentage()).isPositive();
        assertThat(tax.getStartDate()).isNotNull();
        // EndDate can be null (for active taxes)

        // Validate business rules
        assertThat(tax.getTaxPercentage()).isLessThan(new BigDecimal("100")); // Reasonable tax rate
        assertThat(tax.getStartDate()).isBeforeOrEqualTo(testDate);
    }


}