package com.gl.planesAndAirfileds.simulator.service;

import com.gl.planesAndAirfileds.simulator.domain.FlightDetails;
import com.gl.planesAndAirfileds.simulator.domain.Plane;

import java.util.List;

/**
 * Created by marcin.majka on 1/3/2017.
 */

public interface GeneratePlaneLogDataService {

    List<String> getListOfPlanes();

    void generatePlaneDataLog(Plane plane) throws InterruptedException;

    void changePlainCourse(String sid, Double latitude, Double longitude);

    public void updateFlightDetails(FlightDetails flightDetails);

    public FlightDetails generateStartingFlightDetails(Plane plane);
}
