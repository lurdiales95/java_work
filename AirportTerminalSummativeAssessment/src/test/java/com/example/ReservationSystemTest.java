package com.example;


import com.example.data.CSVUtil;
import com.example.domain.*;

import  org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;


import java.util.HashMap;
import java.util.Scanner;



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
            Scanner scanner = new Scanner(new File(filename));
            assertTrue(scanner.hasNextLine());

            String actualLine = scanner.nextLine();
            assertTrue(actualLine.contains("AA101"));
            assertTrue(actualLine.contains("John Doe"));
            assertTrue(actualLine.contains("P12345"));
            assertTrue(actualLine.contains("Boeing 737"));
            assertTrue(actualLine.contains("Commercial"));

            scanner.close();

        } catch (IOException e) {
            fail("Failed to read CSV file: " + e.getMessage());
        }
    }

        @Test
        //CommercialAircraft vs. PrivateJet
        public void testAircraftTypesWithFlights() {

            CommercialAircraft commercial = new CommercialAircraft("Boeing 737", 180, 5000, "Delta");
            Flight commercialFlight = new Flight("AA101", LocalDate.now(), BigDecimal.valueOf(299.99), commercial);

            PrivateJet privateJet = new PrivateJet("Gulfstream G650", 12, 2000.0, true, 900);
            Flight privateFlight = new Flight("PJ001", LocalDate.now(), BigDecimal.valueOf(5000.00), privateJet);

            //CommercialAircraft testing
            assertEquals("Boeing 737", commercial.getModel());
            assertEquals("Delta", commercial.getAirlineName());
            assertEquals(commercial, commercialFlight.getAircraft());
            assertEquals(180, commercial.getCapacity());

            //PrivateJet testing
            assertEquals("Gulfstream G650", privateJet.getModel());
            assertTrue(privateJet.getHasLuxuryService());
            assertEquals(privateJet, privateFlight.getAircraft());
            assertEquals(900, privateJet.getMaxSpeed());
        }

    @Test
    public void testGetPassengersForFlight() {

        ReservationSystem reservationSystem = new ReservationSystem();
        Passenger passenger1 = new Passenger("Alicia Villa", "G3245346547");
        Passenger passenger2 = new Passenger("Leonard Barker", "T3945855458");
        String flightNumber = "AA101";


        reservationSystem.addReservation(flightNumber, passenger1);
        reservationSystem.addReservation(flightNumber, passenger2);

        ArrayList<Passenger> passengers = reservationSystem.getPassengersForFlight(flightNumber);

        assertEquals(2, passengers.size());
        assertEquals("Alicia Villa", passengers.get(0).getName());
        assertEquals("Leonard Barker", passengers.get(1).getName());

    }

    @Test
    public void testLoadReservationsFromCSV() {
        String filename = "test_load.csv";

        ReservationSystem reservationSystem = new ReservationSystem();
        Passenger passenger = new Passenger("Ginny Pig", "G35624536");
        String flightNumber = "Fake Flight1234";

        reservationSystem.addReservation(flightNumber, passenger);  // Fixed

        Aircraft aircraft = new CommercialAircraft("Fake Flight1234", 100, 1000, "Fake Airline");
        Flight flight = new Flight(flightNumber, LocalDate.now(), BigDecimal.valueOf(500), aircraft);

        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(flight);  // Added missing line

        CSVUtil.saveReservationsToCSV(filename, reservationSystem, flights);

        HashMap<String, ArrayList<Passenger>> loadedReservations = CSVUtil.loadReservationsFromCSV(filename);

        // Fixed Assert section:
        assertEquals(1, loadedReservations.size());  // Contains 1 flight in the HashMap
        assertTrue(loadedReservations.containsKey("Fake Flight1234"));  // Has correct flight number
        assertEquals(1, loadedReservations.get("Fake Flight1234").size());  // Contains 1 passenger on the flight
        assertEquals("Ginny Pig", loadedReservations.get("Fake Flight1234").get(0).getName());  // Retrieves correct passenger name
    }
}

