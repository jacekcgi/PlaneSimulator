package com.gl.planesAndAirfileds.simulator.domain;

import java.io.Serializable;

/**
 * Created by marek.sobieraj on 2017-03-27.
 */
public class FakeGeneratedData implements Serializable {

    private double maxVelocity;

    // liters per km
    private double basedFuelConsumption;

    // liters
    private double fuelCapacity;

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public double getBasedFuelConsumption() {
        return basedFuelConsumption;
    }

    public void setBasedFuelConsumption(double basedFuelConsumption) {
        this.basedFuelConsumption = basedFuelConsumption;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }
}
