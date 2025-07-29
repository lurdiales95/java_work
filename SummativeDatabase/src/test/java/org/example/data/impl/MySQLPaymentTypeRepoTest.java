package org.example.data.impl;

import org.example.data.exceptions.InternalErrorException;
import org.example.model.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLPaymentTypeRepoTest {

    private MySQLPaymentTypeRepo paymentTypeRepo;
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/SimpleBistroTest");
        dataSource.setUsername("root");
        dataSource.setPassword("J1r0Dr34ms0fSush1");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        jdbcTemplate = new JdbcTemplate(dataSource);

        paymentTypeRepo = new MySQLPaymentTypeRepo(jdbcTemplate);


        try {
            jdbcTemplate.execute("CALL set_known_good_state()");
        } catch (Exception e) {
            System.out.println("Warning: Could not reset test data - " + e.getMessage());
        }

    }

    @Test
    void getAll_ShouldReturnAllPaymentTypes() throws InternalErrorException {

        List<PaymentType> result = paymentTypeRepo.getAll();

        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Should return at least one payment type");

        System.out.println("Found " + result.size() + " payment types");

        PaymentType firstType = result.get(0);
        assertNotNull(firstType.getPaymentTypeName(), "Payment type name should not be null");
        assertTrue(firstType.getPaymentTypeID() > 0, "Payment type ID should be positive");

        for (PaymentType pt : result) {
            System.out.println("PaymentType ID: " + pt.getPaymentTypeID() +
                    ", Name: " + pt.getPaymentTypeName());
        }
    }


    @Test
    void getAll_ShouldReturnExpectedPaymentTypes() throws InternalErrorException {
        List<PaymentType> result = paymentTypeRepo.getAll();

        assertTrue(result.size() >= 5, "Should have at least 5 payment types");

        boolean hasCash = result.stream()
                .anyMatch(pt -> "Cash".equals(pt.getPaymentTypeName()));
        boolean hasVisa = result.stream()
                .anyMatch(pt -> "Visa".equals(pt.getPaymentTypeName()));

        assertTrue(hasCash, "Should have Cash payment type");
        assertTrue(hasVisa, "Should have Visa payment type");
    }

    @Test
    void getAll_ShouldREturnValidData() throws InternalErrorException {

        List<PaymentType> result = paymentTypeRepo.getAll();

        for (PaymentType pt : result) {
            assertTrue(pt.getPaymentTypeID() > 0, "All payment types should have positive IDs");
            assertNotNull(pt.getPaymentTypeName(), "All payment types should have names");
            assertFalse(pt.getPaymentTypeName().trim().isEmpty(), "Payment type names should not be empty");
        }
    }

    @Test
    void getAll_ShouldNotThrowException() {
        // Assert - Method should execute without throwing exceptions
        assertDoesNotThrow(() -> {
            List<PaymentType> result = paymentTypeRepo.getAll();
            assertNotNull(result);
        });
    }
}