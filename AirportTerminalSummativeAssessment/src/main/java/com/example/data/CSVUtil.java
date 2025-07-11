package com.example.data;

import com.example.ReservationSystem;
import com.example.domain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CSVUtil {

    public static HashMap<String, ArrayList<Passenger>> loadReservationsFromCSV(String filename) {
        HashMap<String, ArrayList<Passenger>> reservations = new HashMap<>();

        try {

            Scanner scanner = new Scanner(new File(filename));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(",");

                String flightNumber = parts[0];
                String passengerName = parts[3];
                String passportNumber = parts[4];


                Passenger passenger = new Passenger(passengerName, passportNumber);

                if (reservations.containsKey(flightNumber)) {
                    reservations.get(flightNumber).add(passenger);

                } else {

                    ArrayList<Passenger> passengerList = new ArrayList<>();
                    passengerList.add(passenger);
                    reservations.put(flightNumber, passengerList);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return reservations;

    }

    public static void saveReservationsToCSV(String filename, ReservationSystem reservationSystem, ArrayList<Flight> flights) {

        try {
            FileWriter writer = new FileWriter(filename);

            for (String flightNumber : reservationSystem.getReservations().keySet()) {
                ArrayList<Passenger> passengers = reservationSystem.getPassengersForFlight(flightNumber);

                Flight matchingFlight = null;
                for (Flight flight : flights) {
                    if (flight.getFlightNumber().equals(flightNumber)) {
                        matchingFlight = flight;
                        break;
                    }
                }
                if (matchingFlight != null) {

                    for (Passenger passenger : passengers) {
                        String passengerName = passenger.getName();
                        String passportNumber = passenger.getPassportNumber();

                        LocalDate departureDate = matchingFlight.getDepartureDate();
                        BigDecimal ticketPrice = matchingFlight.getTicketPrice();

                        Aircraft aircraft = matchingFlight.getAircraft();
                        String aircraftModel = aircraft.getModel();

                        String aircraftType = "";
                        if (aircraft instanceof CommercialAircraft) {
                            aircraftType = "Commercial";

                        } else if (aircraft instanceof PrivateJet) {
                            aircraftType = "PrivateJet";
                        }

                        String csvLine = flightNumber + "," + departureDate + "," + ticketPrice + "," + passengerName + "," + passportNumber + "," + aircraftModel + "," + aircraftType;
                        writer.write(csvLine + "\n");
                    }
                } else {
                    System.out.println("Warning: Could not find flight details for flight number: " + flightNumber);
                }

            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}