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
public class PlaneDataUtil {
    //Radius in km
    private final static float EARTH_RADIUS = 6371;
    private final static double DESCENT_DISTANCE = 100;
    private final static double LANDING_DISTANCE = 10;
    private final static long TAKEOFF_TIME_IN_SECONDS = 30;

    private static double calculateFinalCourse(double srcLat, double srcLon, double destLat, double destLon) {
        double longDiff = destLon - srcLon;
        double y = Math.sin(longDiff) * Math.cos(destLat);
        double x = Math.cos(srcLat) * Math.sin(destLat) - Math.sin(srcLat) * Math.cos(destLat) * Math.cos(longDiff);
        double bearing = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
        return (bearing + 180) % 360;
    }

    public static Point calculateDestinationPoint(double latitude, double longitude, double course, double distance) {
        double angularDistance = distance/EARTH_RADIUS;
        latitude = Math.toRadians(latitude);
        longitude = Math.toRadians(longitude);
        course = Math.toRadians(course);

        double latitudeEnd = Math.asin(Math.sin(latitude) * Math.cos(angularDistance) +
                Math.cos(latitude) * Math.sin(angularDistance) * Math.cos(course));


        double longitudeEnd = longitude + Math.atan2(Math.sin(course) * Math.sin(angularDistance) * Math.cos(latitude),
                Math.cos(angularDistance) - Math.sin(latitude) * Math.sin(latitudeEnd));

        double courseEnd = calculateFinalCourse(latitudeEnd, longitudeEnd, latitude, longitude);
        return new Point(Math.toDegrees(latitudeEnd), Math.toDegrees(longitudeEnd), courseEnd);
    }

    public static FlightPhase calculateFlightPhaseChange(FlightPhase currentFlightPhase, LocalDateTime flightStartTime,double distanceTraveled,double flightDistance) {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        FlightPhase newFlightPhase = currentFlightPhase;
        switch (currentFlightPhase) {
            case TAKE_OFF:
                Duration between = Duration.between(flightStartTime, utc.toLocalDateTime());
                if (between.getSeconds() >TAKEOFF_TIME_IN_SECONDS) {
                    newFlightPhase = FlightPhase.CRUISE;
                }
                break;
            case CRUISE:
                if(flightDistance - distanceTraveled <= DESCENT_DISTANCE) {
                    newFlightPhase = FlightPhase.DESCENT;
                }
                break;
            case DESCENT:
                if(flightDistance - distanceTraveled <= LANDING_DISTANCE) {
                    newFlightPhase = FlightPhase.LANDING;
                }
                break;
            case LANDED:
                if(flightDistance - distanceTraveled <= 0) {
                    newFlightPhase = FlightPhase.LANDED;
                }
                break;
        }

        return newFlightPhase;
    }

    public static double calculateCurrentVelocity(FlightPhase flightPhase, double maxVelocity ) {

        if(flightPhase.equals(FlightPhase.LANDED)) {
            return 0;
        }
        return maxVelocity+maxVelocity*flightPhase.getVelocityChange()/100;

    }

}
