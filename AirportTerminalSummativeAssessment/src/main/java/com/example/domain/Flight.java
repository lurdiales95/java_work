package com.example.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Flight {
    private String flightNumber;
    private LocalDate departureDate;
    private BigDecimal ticketPrice;
    private Aircraft aircraft;

    public Flight(String flightNumber, LocalDate departureDate, BigDecimal ticketPrice, Aircraft aircraft) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.ticketPrice = ticketPrice;
        this.aircraft = aircraft;

    }
    public String getFlightNumber() {
        return flightNumber;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    @Override
    public boolean equals(Object otherFlight) {
        if (this == otherFlight) return true;
        if (otherFlight == null || getClass() != otherFlight.getClass()) return false;
        Flight flight = (Flight) otherFlight;
        return Objects.equals(flightNumber, flight.flightNumber) && Objects.equals(departureDate, flight.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, departureDate);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", departureDate=" + departureDate +
                '}';
    }
}




/* Attributes: flightNumber, departureDate (LocalDate), ticketPrice (BigDecimal),
Aircraft Constructor, getters, and toString() method */