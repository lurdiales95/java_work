package com.example;

import com.example.data.CSVUtil;
import com.example.domain.Aircraft;
import com.example.domain.Flight;
import com.example.domain.Passenger;
import com.example.domain.PrivateJet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AirportTerminalApp {

    public static void main(String[] args) {
        ReservationSystem reservationSystem = new ReservationSystem();
        Aircraft aircraft = new PrivateJet("747", 300, 100.30, true, 10000);
        Flight flight1 = new Flight("A25", LocalDate.now(), BigDecimal.valueOf(1000), aircraft);
        Flight flight2 = new Flight("A3353", LocalDate.now(), BigDecimal.valueOf(2000), aircraft);
        Passenger Robert = new Passenger("Robert Par", "GTO2343253435");
        Passenger Violet = new Passenger("Violet Ledger", "GF2338534543");

        reservationSystem.addReservation(flight1.getFlightNumber(), Robert);
        reservationSystem.addReservation(flight1.getFlightNumber(), Violet);

        System.out.println("Current reservations:");
        System.out.println(reservationSystem.getReservations().entrySet());

        //Save to CSV
        System.out.println("\nSaving reservations to CSV file...");
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);

        CSVUtil.saveReservationsToCSV("reservations.csv", reservationSystem, flights);
        System.out.println("Reservations saved to reservations.csv");

        //Load from CSV
        System.out.println("\nLoading reservations from CSV file...");
        HashMap<String, ArrayList<Passenger>> loadedReservations = CSVUtil.loadReservationsFromCSV("reservations.csv");

        System.out.println("Loaded reservations:");
        for (String flightNum : loadedReservations.keySet()) {
            ArrayList<Passenger> passengers = loadedReservations.get(flightNum);
            System.out.println("Flight " + flightNum + " has " + passengers.size() + " passengers:");
            for (Passenger p : passengers) {
                System.out.println("  - " + p.getName() + " (" + p.getPassportNumber() + ")");
            }
        }
    }
}
