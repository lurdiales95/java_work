package com.example.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

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
}


/* Attributes: flightNumber, departureDate (LocalDate), ticketPrice (BigDecimal),
Aircraft Constructor, getters, and toString() method */