package com.example;


import com.example.data.CSVUtil;
import com.example.domain.Aircraft;
import com.example.domain.CommercialAircraft;
import com.example.domain.Flight;
import  org.junit.Test;

import static java.nio.file.Paths.get;
import static org.junit.Assert.*;
import com.example.domain.Passenger;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;


public class ReservationSystemTest {

    @Test
    public void testAddReservation() {
        ReservationSystem reservationSystem = new ReservationSystem();
        Passenger passenger = new Passenger("John Doe", "P12345");
        String flightNumber = "AA101";

        reservationSystem.addReservation(flightNumber, passenger);

        ArrayList <Passenger> passengers = reservationSystem.getPassengersForFlight(flightNumber);

        assertEquals(1,passengers.size());
        assertEquals("John Doe", passengers.get(0).getName());
        assertEquals("P12345", passengers.get(0).getPassportNumber());

    }

    @Test
    public void testSaveReservationsToCSV() {
        ReservationSystem reservationSystem = new ReservationSystem();
        Passenger passenger = new Passenger("John Doe", "P12345");
        String flightNumber = "AA101";

        reservationSystem.addReservation(flightNumber, passenger);

        Aircraft aircraft = new CommercialAircraft("Boeing 737", 180, 5000, "Delta");
        Flight flight = new Flight("AA101", LocalDate.now(), BigDecimal.valueOf(299.99), aircraft);

        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(flight);

        String filename = "test.csv";
        CSVUtil.saveReservationsToCSV(filename, reservationSystem, flights);

        File csvFile = new File(filename);
        assertTrue(csvFile.exists());

        try {
            List<String> lines = File.readAllLines(Paths.get(filename));
            assertEquals(1, lines.size());


            String actualLine = lines.get(0);
            assertTrue(actualLine.contains("AA101"));
            assertTrue(actualLine.contains("John Doe"));
            assertTrue(actualLine.contains("P12345"));
            assertTrue(actualLine.contains("Boeing 737"));
            assertTrue(actualLine.contains("Commercial"));

        } catch (IOException e) {
            fail("Failed to read CSV file: " + e.getMessage());
        }


    }

    @Test
    public void testGetPassengersForFlight() {
    }

    @Test
    public void testGetReservations() {
    }
}