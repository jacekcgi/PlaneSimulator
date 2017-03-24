package com.gl.planesAndAirfileds.simulator.util;

import com.gl.planesAndAirfileds.simulator.enums.FlightPhase;

import java.util.Random;

/**
 * Created by marcin.majka on 6/3/2017.
 */
public class FuelConsumptionUtil {

    public static double calculateCurrentFuelConsumption(FlightPhase flightPhase, double averageFuelConsumptionLPerKm) {

        if (flightPhase.equals(FlightPhase.LANDED)) {
            return 0;
        }
        double fuelConsumption = averageFuelConsumptionLPerKm + averageFuelConsumptionLPerKm * flightPhase
                .getFuelConsumptionChange() / 100;
        return fuelConsumption;
    }

    public static double calculatePlaneDistanceInKm(double averageFuelConsumptionLPerKm, double fuelCapacityInLiter) {
        Random random = new Random();
        double maxDistance = fuelCapacityInLiter / averageFuelConsumptionLPerKm;
        double minDistance = maxDistance * 0.1;
        maxDistance = maxDistance - minDistance;
        double distance = random.doubles(minDistance, maxDistance).limit(1).findFirst().getAsDouble();
        return distance;
    }

    public static double calculateRemainingFuel(double fuelCapacity, double averageFuelConsumption, double distance) {
        return fuelCapacity - averageFuelConsumption * distance;
    }
}
