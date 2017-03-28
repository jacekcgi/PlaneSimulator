package com.gl.planesAndAirfileds.simulator.domain;

import com.gl.planesAndAirfileds.simulator.enums.FlightPhase;

import java.io.Serializable;

/**
 * Velocity in km/h;
 * capacity in liter;
 * time in milliseconds
 */
public class PostFlightDetailsDto implements Serializable {
    private double gpsLatitude;

    private double gpsLongitude;

    private double velocity;

    private double remainingFuel;

    private double averageFuelConsumption;

    private double distanceTraveled;

    private String flightRouteSid;

    private FlightPhase flightPhase;

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public double getRemainingFuel() {
        return remainingFuel;
    }

    public void setRemainingFuel(double remainingFuel) {
        this.remainingFuel = remainingFuel;
    }

    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(double averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public String getFlightRouteSid() {
        return flightRouteSid;
    }

    public void setFlightRouteSid(String flightRouteSid) {
        this.flightRouteSid = flightRouteSid;
    }

    public FlightPhase getFlightPhase() {
        return flightPhase;
    }

    public void setFlightPhase(FlightPhase flightPhase) {
        this.flightPhase = flightPhase;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("flightRouteSid:" + flightRouteSid + "\n");
        sb.append("velocity:" + velocity + "\n");
        sb.append("flightPhase:" + flightPhase + "\n");
        sb.append("distanceTraveled:" + distanceTraveled + "\n");
        sb.append("gpsLatitude:" + gpsLatitude + "\n");
        sb.append("gpsLongitude:" + gpsLongitude + "\n");
        sb.append("remainingFuel:" + remainingFuel + "\n");
        sb.append("averageFuelConsumption:" + averageFuelConsumption + "\n");
        return sb.toString();
    }
}
