package com.gl.planesAndAirfileds.simulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDetails {
    public FlightDetails(String gpsLatitude, String gpsLongitude, Float course, Float velocity,Plane plane) {
        this.gpsLatitude = gpsLatitude;
        this.gpsLongitude = gpsLongitude;
        this.course = course;
        this.velocity = velocity;
        this.plane = plane;
    }
    public FlightDetails() {

    }
    private String gpsLatitude;
    private String gpsLongitude;
    private Float course;
    private Float velocity;
    private Integer averageFuelConsumption;
    private Date incomingTime;
    private Plane plane;

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public Integer getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(Integer averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public Date getIncomingTime() {
        return incomingTime;
    }

    public void setIncomingTime(Date incomingTime) {
        this.incomingTime = incomingTime;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Float getCourse() {
        return course;
    }

    public void setCourse(Float course) {
        this.course = course;
    }

    public Float getVelocity() {
        return velocity;
    }

    public void setVelocity(Float velocity) {
        this.velocity = velocity;
    }
}
