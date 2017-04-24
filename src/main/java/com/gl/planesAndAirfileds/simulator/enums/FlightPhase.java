package com.gl.planesAndAirfileds.simulator.enums;

/**
 * Created by marcin.majka on 6/3/2017.
 */
public enum FlightPhase {
    READY(30, 40),
    TAKE_OFF(30, 40),
    CRUISE(-10, 0),
    DESCENT(-20, 90),
    LANDING(-5, 20),
    LANDED(-100, 100);

    private int fuelConsumptionChange;

    private int velocityChange;

    private FlightPhase(int fuelConsumptionChange, int velocityChange) {
        this.fuelConsumptionChange = fuelConsumptionChange;
        this.velocityChange = velocityChange;
    }

    public int getFuelConsumptionChange() {
        return fuelConsumptionChange;
    }

    public void setFuelConsumptionChange(int fuelConsumptionChange) {
        this.fuelConsumptionChange = fuelConsumptionChange;
    }

    public int getVelocityChange() {
        return velocityChange;
    }

    public void setVelocityChange(int velocityChange) {
        this.velocityChange = velocityChange;
    }
}
