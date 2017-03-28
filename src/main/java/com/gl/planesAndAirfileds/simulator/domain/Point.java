package com.gl.planesAndAirfileds.simulator.domain;

import java.io.Serializable;

/**
 * Class represent Plane position. GPS coordinates.
 * Created by marcin.majka on 2/3/2017.
 */
public class Point implements Serializable {
    private final double latitude;

    private final double longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
