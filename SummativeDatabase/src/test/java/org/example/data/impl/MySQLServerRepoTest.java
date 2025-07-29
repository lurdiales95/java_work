package org.example.data.impl;

import org.example.data.exceptions.InternalErrorException;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.repository.ServerRepo;
import org.example.model.Server;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class MySQLServerRepoTest {

    @Autowired
    private ServerRepo serverRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private int testServerId;

    @BeforeEach
    void setUp() {
        // Insert a test server for consistent testing
        setupTestServer();
    }

    @AfterEach
    void cleanup() {
        // Clean up test data
        try {
            jdbcTemplate.update("DELETE FROM Server WHERE FirstName = 'Test' AND LastName = 'Server'");
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    private void setupTestServer() {
        try {
            // Clean up any existing test server
            jdbcTemplate.update("DELETE FROM Server WHERE FirstName = 'Test' AND LastName = 'Server'");

            // Insert a test server that's currently active
            jdbcTemplate.update(
                    "INSERT INTO Server (FirstName, LastName, HireDate, TermDate) VALUES (?, ?, ?, ?)",
                    "Test", "Server", LocalDate.now().minusDays(30), null
            );

            // Get the ID of the server we just created
            testServerId = jdbcTemplate.queryForObject(
                    "SELECT ServerID FROM Server WHERE FirstName = 'Test' AND LastName = 'Server'",
                    Integer.class
            );

        } catch (Exception e) {
            System.out.println("Warning: Could not set up test server - " + e.getMessage());
        }
    }

    @Test
    void testGetAllAvailableServers() throws InternalErrorException {
        // Arrange
        LocalDate today = LocalDate.now();

        // Act
        List<Server> availableServers = serverRepo.getAllAvailableServers(today);

        // Assert
        assertThat(availableServers).isNotNull(); // List should NOT be null
        assertThat(availableServers).isNotEmpty(); // Should have at least our test server

        // Verify each server has valid data
        for (Server server : availableServers) {
            assertThat(server.getFirstName()).isNotNull(); // Names should NOT be null
            assertThat(server.getLastName()).isNotNull();
            assertThat(server.getServerID()).isPositive();
            assertThat(server.getHireDate()).isNotNull();

            // Verify the server was hired on or before today
            assertThat(server.getHireDate()).isBeforeOrEqualTo(today);

            // If there's a term date, it should be after today (or null for active servers)
            if (server.getTermDate() != null) {
                assertThat(server.getTermDate()).isAfterOrEqualTo(today);
            }
        }

        // Verify our test server is in the results
        boolean foundTestServer = availableServers.stream()
                .anyMatch(s -> s.getServerID() == testServerId);
        assertThat(foundTestServer).isTrue();
    }

    @Test
    void testGetAllAvailableServers_PastDate() throws InternalErrorException {
        // Test with a date before our test server was hired
        LocalDate pastDate = LocalDate.now().minusDays(60);

        List<Server> availableServers = serverRepo.getAllAvailableServers(pastDate);

        assertThat(availableServers).isNotNull();

        // Our test server should NOT be in this list (hired 30 days ago)
        boolean foundTestServer = availableServers.stream()
                .anyMatch(s -> s.getServerID() == testServerId);
        assertThat(foundTestServer).isFalse();
    }

    @Test
    void testGetAllAvailableServers_FutureDate() throws InternalErrorException {
        // Test with a future date - our current servers should still be available
        LocalDate futureDate = LocalDate.now().plusDays(30);

        List<Server> availableServers = serverRepo.getAllAvailableServers(futureDate);

        assertThat(availableServers).isNotNull();

        // Our test server should be in this list (no term date)
        boolean foundTestServer = availableServers.stream()
                .anyMatch(s -> s.getServerID() == testServerId);
        assertThat(foundTestServer).isTrue();
    }

    @Test
    void testGetServerById() throws InternalErrorException, RecordNotFoundException {
        // Act
        Server server = serverRepo.getServerById(testServerId);

        // Assert
        assertThat(server).isNotNull();
        assertThat(server.getServerID()).isEqualTo(testServerId);
        assertThat(server.getFirstName()).isEqualTo("Test");
        assertThat(server.getLastName()).isEqualTo("Server");
        assertThat(server.getHireDate()).isNotNull();
        // Term date should be null for our active test server
        assertThat(server.getTermDate()).isNull();
    }

    @Test
    void testGetServerByIdNotFound() {
        // Use a very high ID that's unlikely to exist
        int invalidId = 999999;

        // Assert that the correct exception is thrown
        assertThrows(RecordNotFoundException.class, () -> {
            serverRepo.getServerById(invalidId);
        });
    }
}

//    @Test
//    void testGetServerById_WithTerminatedServer() throws InternalErrorException {
//        // Create a terminated server for testing
//        int terminatedServerId = 0;
//        try {
//            jdbcTemplate.update(
//                    "INSERT INTO Server (FirstName, LastName, HireDate, TermDate) VALUES (?, ?, ?, ?)",
//                    "Terminated", "Server", LocalDate.now().minusDays(60), LocalDate.now().minusDays(10)
//            );
//
//            terminatedServerId = jdbcTemplate.queryForObject(
//                    "SELECT ServerID FROM Server WHERE FirstName = 'Terminated' AND LastName = 'Server'",
//                    Integer.class
//            );
//
//            // Should still be able to get the server by ID (even if terminated)
//            Server server = serverRepo.getServerById(terminatedServerId);
//            assertThat(server).isNotNull();
//            assertThat(server.getTermDate()).isNotNull();
//
//            // But they shouldn't appear in available servers for today
//            List<Server> availableServers = serverRepo.getAllAvailableServers(LocalDate.now());
//            boolean foundTerminatedServer = availableServers.stream()
//                    .anyMatch(s -> s.getServerID() == terminatedServerId);
//            assertThat(foundTerminatedServer).isFalse();
//
//        } catch (RecordNotFoundException e) {
//            fail("Should be able to retrieve terminated server by ID");
//        } finally {
//            // Cleanup
//            if (terminatedServerId > 0) {
//                try {
//                    jdbcTemplate.update("DELETE FROM Server WHERE ServerID = ?", terminatedServerId);
//                } catch (Exception e) {
//                    // Ignore cleanup errors
//                }
//            }
//        }
//    }
