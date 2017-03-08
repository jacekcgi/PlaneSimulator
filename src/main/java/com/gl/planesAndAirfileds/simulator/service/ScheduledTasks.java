package com.gl.planesAndAirfileds.simulator.service;

import com.gl.planesAndAirfileds.simulator.domain.Plane;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by marcin.majka on 1/3/2017.
 */
@Component
public class ScheduledTasks {

    private int counter = 0;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    GeneratePlaneLogDataService planeLogDataService;

    public ScheduledTasks(GeneratePlaneLogDataService planeLogDataService) {
        this.planeLogDataService = planeLogDataService;
    }



    @Scheduled(fixedRate = 20000)
    public void generatePlaneData() {
        List<Plane> listOfPlanes = planeLogDataService.getListOfPlanes();

       for(Plane plane:listOfPlanes) {
           try {
               planeLogDataService.generatePlaneDataLog(plane);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

}
