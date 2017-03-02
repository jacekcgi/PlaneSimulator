package com.gl.planesAndAirfileds.simulator.service;

import com.gl.planesAndAirfileds.simulator.domain.FlightDetails;
import com.gl.planesAndAirfileds.simulator.domain.Plane;
import com.gl.planesAndAirfileds.simulator.util.PlaneDataUtil;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by marcin.majka on 1/3/2017.
 */

@Service
public class GeneratePlaneLogDataService {


    private Map<Long, FlightDetails> flightDetailsMap = new ConcurrentHashMap<>();
    private static final String SERVER_ADDRESS = "http://localhost:8080/";

    RestTemplate restTemplate;

    public GeneratePlaneLogDataService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public List<Plane> getListOfPlanes() {
        ResponseEntity<Plane[]> planeResponseEntity = restTemplate.getForEntity(SERVER_ADDRESS +"planeIdList", Plane[].class);
        List<Plane> planeList = null;
        if (planeResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            planeList = Arrays.asList(planeResponseEntity.getBody());
        } else {
            planeList = new ArrayList<>();
        }
        return planeList;
    }

    @Async
    public void generatePlaneDataLog(Plane plane) throws InterruptedException {
        FlightDetails flightDetails = flightDetailsMap.get(plane.getId());
        if (flightDetails == null) {
            System.out.println("Tworze");
            flightDetails = PlaneDataUtil.generateStartingFlightDetails(plane);
            flightDetailsMap.put(plane.getId(), flightDetails);

        } else {
            System.out.println("Updatuje !!!!!!");
            PlaneDataUtil.updatePosition(flightDetails);
        }

        System.out.println(flightDetails);
      //  URI uri = restTemplate.postForLocation(SERVER_ADDRESS + "flightDetails", flightDetails,FlightDetails.class);
       // System.out.println(uri);
    }
}
