package com.gl.planesAndAirfileds.simulator.util;

import com.gl.planesAndAirfileds.simulator.domain.Point;
import com.gl.planesAndAirfileds.simulator.enums.FlightPhase;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Created by marcin.majka on 1/3/2017.
 */
public final class PlaneDataUtil {
    //Radius in km
    private final static float EARTH_RADIUS = 6371;

    private final static double DESCENT_DISTANCE = 100;

    private final static double LANDING_DISTANCE = 10;

    private final static long TAKEOFF_TIME_IN_SECONDS = 30;

    /**
     * Coordinates in radian
     *
     * @param srcLat
     * @param srcLon
     * @param destLat
     * @param destLon
     * @return
     */
    public static double calculateFinalCourseRadians(double srcLat, double srcLon, double destLat, double destLon) {
        double bearing = calculateNewCourseRadians(srcLat, srcLon, destLat, destLon);
        return (bearing + 180) % 360;
    }

    public static double calculateNewCourseRadians(double srcLat, double srcLon, double destLat, double destLon) {
        double longDiff = destLon - srcLon;
        double y = Math.sin(longDiff) * Math.cos(destLat);
        double x = Math.cos(srcLat) * Math.sin(destLat) - Math.sin(srcLat) * Math.cos(destLat) * Math.cos(longDiff);
        double bearing = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
        return bearing;
    }

    /**
     * Coordinates in degree
     *
     * @param latitude
     * @param longitude
     * @param course
     * @param distance
     * @return
     */
    public static Point calculateDestinationPoint(double latitude, double longitude, double course, double distance) {
        double angularDistance = distance / EARTH_RADIUS;
        latitude = Math.toRadians(latitude);
        longitude = Math.toRadians(longitude);

        course = Math.toRadians(course);

        double latitudeEnd = Math.asin(Math.sin(latitude) * Math.cos(angularDistance) +
                Math.cos(latitude) * Math.sin(angularDistance) * Math.cos(course));

        double longitudeEnd = longitude + Math.atan2(Math.sin(course) * Math.sin(angularDistance) * Math.cos(latitude),
                Math.cos(angularDistance) - Math.sin(latitude) * Math.sin(latitudeEnd));

        return new Point(Math.toDegrees(latitudeEnd), Math.toDegrees(longitudeEnd));
    }

    public static FlightPhase calculateFlightPhaseChange(FlightPhase currentFlightPhase, LocalDateTime flightStartTime,
                                                         double distanceTraveled, double flightDistance) {
        LocalDateTime now = LocalDateTime.now();
        // starting our plane...
        FlightPhase newFlightPhase = FlightPhase.TAKE_OFF;

        if (currentFlightPhase == FlightPhase.TAKE_OFF) {
            Duration between = Duration.between(flightStartTime, now);
            if (between.getSeconds() > TAKEOFF_TIME_IN_SECONDS) {
                newFlightPhase = FlightPhase.CRUISE;
            }
        }
        else {
            double distanceToTravel = flightDistance - distanceTraveled;
            if (distanceToTravel <= 0) {
                newFlightPhase = FlightPhase.LANDED;
            }
            else if (distanceToTravel <= LANDING_DISTANCE) {
                newFlightPhase = FlightPhase.LANDING;
            }
            else if (distanceToTravel <= DESCENT_DISTANCE) {
                newFlightPhase = FlightPhase.DESCENT;
            }
            else {
                newFlightPhase = FlightPhase.CRUISE;
            }
        }
        return newFlightPhase;
    }

    public static double calculateCurrentVelocity(FlightPhase flightPhase, double maxVelocity) {

        if (flightPhase.equals(FlightPhase.LANDED)) {
            return 0;
        }
        return maxVelocity + maxVelocity * flightPhase.getVelocityChange() / 100;

    }

    /**
     * Coordinates in degree
     *
     * @param srcLat
     * @param srcLon
     * @param destLat
     * @param destLon
     * @return
     */
    public static double calculateFlightDistanceBetweenPointsDegree(double srcLat, double srcLon, double destLat,
                                                                    double destLon) {
        return calculateFlightDistanceBetweenPointsRadians(Math.toRadians(srcLat), Math.toRadians(srcLon),
                Math.toRadians(destLat), Math.toRadians(destLon));

    }

    /**
     * Coordinates in radian.
     *
     * @param srcLat
     * @param srcLon
     * @param destLat
     * @param destLon
     * @return distance in km
     */
    public static double calculateFlightDistanceBetweenPointsRadians(double srcLat, double srcLon, double destLat,
                                                                     double destLon) {
        double dLat = destLat - srcLat;
        double dLon = destLon - srcLon;

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(srcLat) * Math
                .cos(destLat);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;

    }

}
