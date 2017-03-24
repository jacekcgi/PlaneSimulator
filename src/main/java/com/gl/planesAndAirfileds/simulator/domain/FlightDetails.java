package com.gl.planesAndAirfileds.simulator.domain;

import com.gl.planesAndAirfileds.simulator.enums.FlightPhase;

import java.time.LocalDateTime;

/**
 * Velocity in km/h; capacity in liter; time in milliseconds
 */
public class FlightDetails {
    public FlightDetails(long incomingTime, Double gpsLatitude, Double gpsLongitude, Double course, Double maxVelocity,
                         Double velocity, Plane plane,
                         FlightPhase flightPhase, Double fuelCapacity, Double basedFuelConsumption,
                         Double averageFuelConsumption, LocalDateTime flightStartTime, Double flightDistance) {

        this.incomingTime = incomingTime;
        this.gpsLatitude = gpsLatitude;
        this.gpsLongitude = gpsLongitude;
        this.course = course;
        this.maxVelocity = maxVelocity;
        this.velocity = velocity;
        this.plane = plane;
        this.flightPhase = flightPhase;
        this.fuelCapacity = fuelCapacity;
        this.remainingFuel = fuelCapacity;
        this.basedFuelConsumption = basedFuelConsumption;
        this.averageFuelConsumption = averageFuelConsumption;
        this.flightStartTime = flightStartTime;
        this.flightDistance = flightDistance;
        this.distanceTraveled = 0d;
        this.flightTime = 0l;
        if (flightPhase.equals(FlightPhase.LANDED)) {
            this.isLanded = true;
        }
        else {
            this.isLanded = false;
        }
    }

    public FlightDetails() {

    }

    private LocalDateTime flightStartTime;

    private Double flightDistance;

    private Double gpsLatitude;

    private Double gpsLongitude;

    private Double course;

    private Double maxVelocity;

    private Double velocity;

    private Long incomingTime;

    private Long flightTime;

    private Double fuelCapacity;

    private Double remainingFuel;

    private Double basedFuelConsumption;

    private Double averageFuelConsumption;

    private Double distanceTraveled;

    private Plane plane;

    private boolean isLanded;

    private FlightPhase flightPhase;

    public Double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(Double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public Long getIncomingTime() {
        return incomingTime;
    }

    public void setIncomingTime(Long incomingTime) {
        this.incomingTime = incomingTime;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public FlightPhase getFlightPhase() {
        return flightPhase;
    }

    public void setFlightPhase(FlightPhase flightPhase) {
        this.flightPhase = flightPhase;
        if (flightPhase.equals(FlightPhase.LANDED)) {
            this.isLanded = true;
        }
    }

    public Double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Double getBasedFuelConsumption() {
        return basedFuelConsumption;
    }

    public void setBasedFuelConsumption(Double basedFuelConsumption) {
        this.basedFuelConsumption = basedFuelConsumption;
    }

    public Double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(Double averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public LocalDateTime getFlightStartTime() {
        return flightStartTime;
    }

    public void setFlightStartTime(LocalDateTime flightStartTime) {
        this.flightStartTime = flightStartTime;
    }

    public Double getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(Double flightDistance) {
        this.flightDistance = flightDistance;
    }

    public Double getRemainingFuel() {
        return remainingFuel;
    }

    public void setRemainingFuel(Double remainingFuel) {
        this.remainingFuel = remainingFuel;
    }

    public Long getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Long flightTime) {
        this.flightTime = flightTime;
    }

    public Double getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(Double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public Double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(Double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void updatePosition(Point point) {
        this.gpsLatitude = point.getLatitude();
        this.gpsLongitude = point.getLongitude();
        this.course = point.getCourse();
    }

    public boolean isLanded() {
        return isLanded;
    }

    public void setLanded(boolean landed) {
        isLanded = landed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("plane: " + plane.getSid());
        sb.append(";latitude: " + gpsLatitude);
        sb.append(";longitude: " + gpsLongitude);
        sb.append(";course:" + course);
        sb.append(";maxVelocity:" + maxVelocity);
        sb.append(";velocity:" + velocity);
        sb.append(";flightPhase:" + flightPhase);
        sb.append(";flightStartTime " + flightStartTime);
        sb.append(";flightDistance " + flightDistance);
        sb.append(";distanceTraveled " + distanceTraveled);
        sb.append(";basedFuelConsumption " + basedFuelConsumption);
        sb.append(";averageFuelConsumption " + averageFuelConsumption);
        sb.append(";fuelCapacity " + fuelCapacity);
        sb.append(";remainingFuel " + remainingFuel);
        sb.append(";flightTime " + flightTime);
        sb.append(";isLanded " + isLanded);
        return sb.toString();
    }
}
