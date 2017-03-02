package com.gl.planesAndAirfileds.simulator.util;

import com.gl.planesAndAirfileds.simulator.domain.FlightDetails;
import com.gl.planesAndAirfileds.simulator.domain.Plane;
import com.gl.planesAndAirfileds.simulator.domain.Point;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;

/**
 * Created by marcin.majka on 1/3/2017.
 */
public class PlaneDataUtil {
    // Speed in km/h
    private final static int MIN_SPEED = 300;
    private final static int MAX_SPEED = 950;
    //Radius in km
    private final static float earthRadius = 6371;

    private static double calculateAngularDistance(long incomingTime, double velocity) {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long now = utc.toInstant().toEpochMilli();
        double flightTimeInHour = (now - incomingTime) / 3600000d;
        return velocity * flightTimeInHour / earthRadius;

    }

    private static double calculateFinalCourse(double srcLat, double srcLon, double destLat, double destLon) {
        double longDiff = destLon - srcLon;
        double y = Math.sin(longDiff) * Math.cos(destLat);
        double x = Math.cos(srcLat) * Math.sin(destLat) - Math.sin(srcLat) * Math.cos(destLat) * Math.cos(longDiff);
        double bearing = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
        return (bearing + 180) % 360;
    }

    public static Point calculateDestinationPoint(double latitude, double longitude, double course, double velocity, long incomingTime) {
        double angularDistance = calculateAngularDistance(incomingTime, velocity);


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

    public static void updatePosition(FlightDetails flightDetails) {
        Point destinationPoint = calculateDestinationPoint(flightDetails.getGpsLatitude(), flightDetails.getGpsLongitude(), flightDetails.getCourse(), flightDetails.getVelocity(), flightDetails.getIncomingTime());
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        flightDetails.updatePosition(destinationPoint, utc.toInstant().toEpochMilli());
    }

    public static FlightDetails generateStartingFlightDetails(Plane plane) {
        Random random = new Random();
        double latitude = random.doubles(-90, 90).limit(1).findFirst().getAsDouble();
        double longitude = random.doubles(-180, 180).limit(1).findFirst().getAsDouble();
        double course = random.doubles(0, 360).limit(1).findFirst().getAsDouble();
        double velocity = random.doubles(MIN_SPEED, MAX_SPEED).limit(1).findFirst().getAsDouble();
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return new FlightDetails(utc.toInstant().toEpochMilli(), latitude, longitude, course, velocity, plane);
    }
}
