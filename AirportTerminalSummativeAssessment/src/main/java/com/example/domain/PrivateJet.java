package com.example.domain;

public class PrivateJet extends Aircraft {
    private boolean hasLuxuryService;
    private int maxSpeed; //jet speed in km/h

    public PrivateJet(String model, int capacity, double fuelCapacity, boolean hasLuxuryService, int maxSpeed) {
        super(model, capacity, fuelCapacity);
        this.hasLuxuryService = hasLuxuryService;
        this.maxSpeed = maxSpeed;
    }

    public boolean getHasLuxuryService() {
        return hasLuxuryService;
    }

    public int getMaxSpeed() {
        return maxSpeed;

    }


}



//
//public String getAirlineName() {
//    return airlineName;
