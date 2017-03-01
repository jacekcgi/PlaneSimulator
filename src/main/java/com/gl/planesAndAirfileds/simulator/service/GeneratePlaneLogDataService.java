package com.gl.planesAndAirfileds.simulator.service;

import com.gl.planesAndAirfileds.simulator.domain.FlightDetails;
import com.gl.planesAndAirfileds.simulator.domain.Plane;
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


    private Map<Long,FlightDetails> flightDetailsMap = new ConcurrentHashMap<>();
    RestTemplate restTemplate;

     public GeneratePlaneLogDataService(RestTemplateBuilder builder) {
         this.restTemplate = builder.build();
    }

    public List<Plane> getListOfPlanes() {
        ResponseEntity<Plane[]> planeResponseEntity = restTemplate.getForEntity("http://localhost:8080/planeIdList",Plane[].class);
        List<Plane> planeList = null;
        if(planeResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            planeList = Arrays.asList(planeResponseEntity.getBody());
        } else {
            planeList = new ArrayList<>();
        }
        return planeList;
    }

    @Async
    public void generatePlaneDataLog(Plane plane) throws InterruptedException {

        FlightDetails flightDetails = flightDetailsMap.get(plane.getId());
        if(flightDetails == null) {
            flightDetails = new FlightDetails("23", "17",30f ,800f ,plane);
            System.out.println("Dodaje");
            flightDetailsMap.put(plane.getId(),flightDetails);
        } else {
            System.out.println("Czytam z bazy");
    }
    }
}
