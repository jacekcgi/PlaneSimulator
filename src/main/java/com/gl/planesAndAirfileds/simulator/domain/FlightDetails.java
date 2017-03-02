package com.gl.planesAndAirfileds.simulator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDetails {
    public FlightDetails(long incomingTime, Double gpsLatitude, Double gpsLongitude, Double course, Double velocity, Plane plane) {
        this.gpsLatitude = gpsLatitude;
        this.gpsLongitude = gpsLongitude;
        this.course = course;
        this.velocity = velocity;
        this.plane = plane;
        this.incomingTime = incomingTime;
    }
    public FlightDetails() {

    }
    private Double gpsLatitude;
    private Double gpsLongitude;
    private Double course;
    private Double velocity;
    private Long incomingTime;
    private Plane plane;

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

    public void updatePosition(Point point,long timestamp) {
        this.gpsLatitude = point.getLatitude();
        this.gpsLongitude = point.getLongitude();
        this.course = point.getCourse();
        this.incomingTime = timestamp;
    }

    @Override
    public String toString() {
        return "plane: "+ plane.getId()+";latitude: " +gpsLatitude+ ";longitude: "+gpsLongitude+";course:"+course+";velocity:"+velocity+";incomingTime:"+incomingTime;

    }
}
