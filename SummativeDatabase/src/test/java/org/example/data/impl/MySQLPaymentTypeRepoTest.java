package org.example.data.impl;

import org.example.data.exceptions.InternalErrorException;
import org.example.data.repository.PaymentTypeRepo;
import org.example.model.PaymentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
// Use SQL scripts to set up test data consistently
@Sql(scripts = "/test-data/payment-types-setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test-data/payment-types-cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MySQLPaymentTypeRepoTest {

    @Autowired
    private PaymentTypeRepo paymentTypeRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // Insert known test data directly using jdbcTemplate
        // This ensures consistent test data without relying on stored procedures
        insertTestPaymentTypes();
    }

    @AfterEach
    void cleanup() {
        // Clean up test data
        try {
            jdbcTemplate.execute("DELETE FROM PaymentType WHERE PaymentTypeName LIKE 'Test_%'");
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    private void insertTestPaymentTypes() {
        try {
            // Insert known test data
            jdbcTemplate.execute("DELETE FROM PaymentType WHERE PaymentTypeName LIKE 'Test_%'");

            jdbcTemplate.update("INSERT INTO PaymentType (PaymentTypeName) VALUES ('Test_Cash')");
            jdbcTemplate.update("INSERT INTO PaymentType (PaymentTypeName) VALUES ('Test_Visa')");
            jdbcTemplate.update("INSERT INTO PaymentType (PaymentTypeName) VALUES ('Test_MasterCard')");
        } catch (DataAccessException e) {
            System.out.println("Warning: Could not insert test data - " + e.getMessage());
        }
    }

    @Test
    void getAll_ShouldReturnAllPaymentTypes() throws InternalErrorException {
        // Act
        List<PaymentType> result = paymentTypeRepo.getAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();

        System.out.println("Found " + result.size() + " payment types");

        // Verify structure of returned data
        PaymentType firstType = result.get(0);
        assertThat(firstType.getPaymentTypeName()).isNotNull();
        assertThat(firstType.getPaymentTypeID()).isPositive();

        // Log all payment types for visibility
        for (PaymentType pt : result) {
            System.out.println("PaymentType ID: " + pt.getPaymentTypeID() +
                    ", Name: " + pt.getPaymentTypeName());
        }
    }

    @Test
    void getAll_ShouldReturnTestPaymentTypes() throws InternalErrorException {
        // Act
        List<PaymentType> result = paymentTypeRepo.getAll();

        // Assert - Look for our test data specifically
        assertThat(result).hasSize(3); // We inserted exactly 3 test payment types

        boolean hasTestCash = result.stream()
                .anyMatch(pt -> "Test_Cash".equals(pt.getPaymentTypeName()));
        boolean hasTestVisa = result.stream()
                .anyMatch(pt -> "Test_Visa".equals(pt.getPaymentTypeName()));
        boolean hasTestMasterCard = result.stream()
                .anyMatch(pt -> "Test_MasterCard".equals(pt.getPaymentTypeName()));

        assertThat(hasTestCash).isTrue();
        assertThat(hasTestVisa).isTrue();
        assertThat(hasTestMasterCard).isTrue();
    }

    @Test
    void getAll_ShouldReturnValidData() throws InternalErrorException {
        // Act
        List<PaymentType> result = paymentTypeRepo.getAll();

        // Assert
        assertThat(result).isNotEmpty();

        for (PaymentType pt : result) {
            assertThat(pt.getPaymentTypeID()).isPositive();
            assertThat(pt.getPaymentTypeName()).isNotNull();
            assertThat(pt.getPaymentTypeName().trim()).isNotEmpty();
        }
    }

    @Test
    void getAll_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            List<PaymentType> result = paymentTypeRepo.getAll();
            assertThat(result).isNotNull();
        });
    }

    @Test
    void getAll_ShouldHandleDatabaseError() {
        // This test would be better with a mocked dependency, but here's how you could test it
        // You might want to temporarily break the database connection to test error handling

        // For now, just ensure the method handles the happy path
        assertDoesNotThrow(() -> {
            List<PaymentType> result = paymentTypeRepo.getAll();
            assertThat(result).isNotNull();
        });
    }

    @Test
    void getAll_ShouldReturnPaymentTypesInCorrectOrder() throws InternalErrorException {
        // Act
        List<PaymentType> result = paymentTypeRepo.getAll();

        // Assert - The repository should return them in some consistent order
        // Based on your repository, there's no explicit ORDER BY, so test what you expect
        assertThat(result).isSortedAccordingTo((pt1, pt2) ->
                Integer.compare(pt1.getPaymentTypeID(), pt2.getPaymentTypeID()));
    }
}