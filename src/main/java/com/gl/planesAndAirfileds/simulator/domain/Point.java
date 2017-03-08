package com.gl.planesAndAirfileds.simulator.domain;

/**
 * Class represent Plane position. GPS coordinates and course;
 * Created by marcin.majka on 2/3/2017.
 */
public class Point {
    private double latitude;
    private double longitude;
    private double course;

    public Point(double latitude, double longitude, double course) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.course = course;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

}
