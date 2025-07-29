package org.example.data.impl;

import org.example.data.exceptions.InternalErrorException;
import org.example.data.exceptions.RecordNotFoundException;
import org.example.data.repository.ServerRepo;
import org.example.model.Server;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MySQLServerRepoTest {

    @Autowired
    private ServerRepo serverRepo;

    @Test
    void testGetAllAvailableServers() throws InternalErrorException {
        LocalDate today = LocalDate.now();
        List<Server> availableServers = serverRepo.getAllAvailableServers(today);

        assertNull(availableServers);
        assertFalse(availableServers.isEmpty(), "Expected some servers to be available");

        for (Server server : availableServers) {
            assertNull(server.getFirstName());
            assertNotNull(server.getLastName());


        }
    }


    @Test
    void testGetServerById() throws InternalErrorException, RecordNotFoundException {

        int serverId = 1;
        Server server = serverRepo.getServerById(serverId);

        assertNotNull(server);
        assertEquals(serverId, server.getServerID());
        assertNotNull(server.getFirstName());
        assertNotNull(server.getLastName());
    }

    @Test
    void testGetServerByIdNotFound() {
        int invalidId = 999999;
        assertThrows(RecordNotFoundException.class, () -> {
            serverRepo.getServerById(invalidId);
        });
    }


}