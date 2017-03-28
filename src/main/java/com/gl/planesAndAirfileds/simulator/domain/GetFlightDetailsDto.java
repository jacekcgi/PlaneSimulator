package com.gl.planesAndAirfileds.simulator.domain;

import com.gl.planesAndAirfileds.simulator.enums.FlightPhase;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by marek.sobieraj on 2017-03-27.
 */
public class GetFlightDetailsDto implements Serializable {

    private double currentLatitude;

    private double currentLongitude;

    private double sourceLatitude;

    private double sourceLongitude;

    private double destinationLatitude;

    private double destinationLongitude;

    private FlightPhase lastFlightPhase;

    private LocalDateTime lastCreatedDate;

    private LocalDateTime startFlightDate;

    private float velocity;

    private String flightRouteSid;

    private double flightDistance;

    private double distanceTraveled;

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public double getSourceLatitude() {
        return sourceLatitude;
    }

    public void setSourceLatitude(double sourceLatitude) {
        this.sourceLatitude = sourceLatitude;
    }

    public double getSourceLongitude() {
        return sourceLongitude;
    }

    public void setSourceLongitude(double sourceLongitude) {
        this.sourceLongitude = sourceLongitude;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public FlightPhase getLastFlightPhase() {
        return lastFlightPhase;
    }

    public void setLastFlightPhase(FlightPhase lastFlightPhase) {
        this.lastFlightPhase = lastFlightPhase;
    }

    public LocalDateTime getLastCreatedDate() {
        return lastCreatedDate;
    }

    public void setLastCreatedDate(LocalDateTime lastCreatedDate) {
        this.lastCreatedDate = lastCreatedDate;
    }

    public LocalDateTime getStartFlightDate() {
        return startFlightDate;
    }

    public void setStartFlightDate(LocalDateTime startFlightDate) {
        this.startFlightDate = startFlightDate;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public String getFlightRouteSid() {
        return flightRouteSid;
    }

    public void setFlightRouteSid(String flightRouteSid) {
        this.flightRouteSid = flightRouteSid;
    }

    public double getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(double flightDistance) {
        this.flightDistance = flightDistance;
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }
}
