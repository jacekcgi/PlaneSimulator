package com.gl.planesAndAirfileds.simulator.controller;

import com.gl.planesAndAirfileds.simulator.domain.Plane;
import com.gl.planesAndAirfileds.simulator.service.GeneratePlaneLogDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by marcin.majka on 8/3/2017.
 */
@RestController
public class PlanesController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private GeneratePlaneLogDataService planeLogDataService;

    @Autowired
    public PlanesController(GeneratePlaneLogDataService planeLogDataService) {

        this.planeLogDataService = planeLogDataService;
    }



    @RequestMapping( value = "/addPlane", method = RequestMethod.POST )
    public ResponseEntity addPlane(@RequestBody Plane plane) {
        if(plane != null) {
            try {
                planeLogDataService.generatePlaneDataLog(plane);
            } catch (InterruptedException e) {
                logger.error("InterruptedException "+e);
                return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return  new ResponseEntity(HttpStatus.OK);
    }
}
