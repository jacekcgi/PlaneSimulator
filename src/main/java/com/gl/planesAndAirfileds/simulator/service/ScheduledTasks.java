package com.gl.planesAndAirfileds.simulator.service;

import com.gl.planesAndAirfileds.simulator.domain.Plane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by marcin.majka on 1/3/2017.
 */
@Component
public class ScheduledTasks {

    private int counter = 0;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    GeneratePlaneLogDataService planeLogDataService;

    public ScheduledTasks(GeneratePlaneLogDataService planeLogDataService) {
        this.planeLogDataService = planeLogDataService;
    }



    @Scheduled(fixedRate = 60000)
    public void generatePlaneData() {
        List<Plane> listOfPlanes = planeLogDataService.getListOfPlanes();

       for(Plane plane:listOfPlanes) {
           try {
               planeLogDataService.generatePlaneDataLog(plane);
           } catch (InterruptedException e) {
               logger.error("InterruptedException ",e);
           }
       }
    }

}
