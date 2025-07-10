package com.example;

import com.example.domain.Aircraft;
import com.example.domain.Flight;
import com.example.domain.Passenger;
import com.example.domain.PrivateJet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AirportTerminalApp
{
    public static void main( String[] args )
    {
        ReservationSystem reservationSystem = new ReservationSystem();
        Aircraft aircraft = new PrivateJet("747",300, 100.30, true, 10000);
        Flight flight = new Flight("A25", LocalDate.now(), BigDecimal.valueOf(1000),  aircraft);
        Flight flight2 = new Flight("A3353", LocalDate.now(), BigDecimal.valueOf(2000), aircraft);
        Passenger Robert = new Passenger("Robert Par", "GTO2343253435");
        Passenger Violet = new Passenger("Violet Ledger", "GF233w4534543");

        reservationSystem.addReservation(flight.getFlightNumber(), Robert);
        // System.out.println(reservationSystem.getPassengersForFlight(flight.getFlightNumber()));
        reservationSystem.addReservation(flight.getFlightNumber(), Violet);


        System.out.println(reservationSystem.getReservations().entrySet());



    }
}
