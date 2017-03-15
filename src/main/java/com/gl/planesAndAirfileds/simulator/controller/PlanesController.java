package com.gl.planesAndAirfileds.simulator.controller;

import com.gl.planesAndAirfileds.simulator.domain.Plane;
import com.gl.planesAndAirfileds.simulator.domain.api.Mappings;
import com.gl.planesAndAirfileds.simulator.service.GeneratePlaneLogDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = Mappings.ADD_PLANE, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void addPlane(@RequestBody Plane plane) throws InterruptedException {

        planeLogDataService.generatePlaneDataLog(plane);
    }

    @RequestMapping(value = Mappings.ADD_NEW_COORDINATES, method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void sendPlaneToCoordinates(@PathVariable(value = "sid") String sid,
                                       @PathVariable(value = "latitude") Double latitude,
                                       @PathVariable(value = "longitude") Double longitude) {

        planeLogDataService.changePlainCourse(sid, latitude, longitude);
    }

}
