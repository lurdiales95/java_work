package org.example.data.impl;

import org.example.data.repository.TaxRepo;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.exceptions.InternalErrorException;
import org.example.model.Tax;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MySQLTaxRepoTest {

    @Autowired
    private TaxRepo taxRepo;

    @Test
    void testGetCurrentTax() throws InternalErrorException, RecordNotFoundException {
        LocalDate testDate = LocalDate.of(2025, 7, 1);
        Tax tax = taxRepo.getCurrentTax(testDate);

        assertNotNull(tax, "Tax should not be null");
        assertTrue(tax.getTaxPercentage().compareTo(BigDecimal.ZERO) > 0, "Tax percentage should be positive");
        assertTrue(!tax.getEffectiveDate().isAfter(Instant.from(testDate)), "Tax effective date should be on or before the test date");
    }

    @Test
    void testGetCurrentTaxThrowsExceptionForFutureDate() {
        LocalDate futureDate = LocalDate.now().plusYears(10);

        assertThrows(RecordNotFoundException.class, () -> {
            taxRepo.getCurrentTax(futureDate);
        });
    }
}