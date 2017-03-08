package com.gl.planesAndAirfileds.simulator.enums;

/**
 * Created by marcin.majka on 6/3/2017.
 */
public enum FlightPhase {
    TAKE_OFF(30,-60),CRUISE(-10,0),DESCENT(-20,-10),LANDING(-5,-80),LANDED(-100,-100);

    private int fuelConsumptionChange;
    private int velocityChange;

    private FlightPhase(int fuelConsumptionChange,int velocityChange) {
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
