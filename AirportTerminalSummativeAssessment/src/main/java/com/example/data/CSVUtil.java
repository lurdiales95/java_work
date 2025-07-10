package com.example.data;

import com.example.ReservationSystem;
import com.example.domain.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CSVUtil {
    private HashMap<String, ArrayList<Passenger>> reservations;

    public CSVUtil(HashMap<String, ArrayList<Passenger>> reservations) {
        this.reservations = reservations;
    }

    public static void loadReservationsFromCSV(String filename) {


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
