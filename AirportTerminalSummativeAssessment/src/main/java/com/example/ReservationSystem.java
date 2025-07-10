package com.example;

import com.example.domain.Flight;
import com.example.domain.Passenger;
import java.util.ArrayList;
import java.util.HashMap;

public class ReservationSystem {
    private HashMap<String, ArrayList<Passenger>> reservations = new HashMap<>();

    public void addReservation(String flightNumber, Passenger passenger) {

        if (reservations.containsKey(flightNumber)) {
            ArrayList<Passenger> passengerList = reservations.get(flightNumber);
            passengerList.add(passenger);

        } else {
            ArrayList<Passenger> newList = new ArrayList<>();
            newList.add(passenger);
            reservations.put(flightNumber, newList);
        }
    }

    public ArrayList<Passenger> getPassengersForFlight(String flightNumber) {
        if (reservations.containsKey(flightNumber)) {
            return reservations.get(flightNumber);

        } else {
            System.out.println("Flight " + flightNumber + " not found");
            return new ArrayList<>();
        }

    }

    public HashMap<String, ArrayList<Passenger>> getReservations() {
        return reservations;
    }


    }








/*


    Stores reservations using HashMap<String,
    ArrayList<Passenger>>
    ○
    Methods:
    ■ addReservation(String flightNumber, Passenger
    passenger)
    ■ List<Passenger> getPassengersForFlight(String
    flightNumber)

 */   // addReservation(String flightNumber, Passenger passenger)