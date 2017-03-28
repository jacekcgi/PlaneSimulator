package com.gl.planesAndAirfileds.simulator.service;

import com.gl.planesAndAirfileds.simulator.domain.GetFlightDetailsDto;
import com.gl.planesAndAirfileds.simulator.domain.PostFlightDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcin.majka on 1/3/2017.
 */
@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private GeneratePlaneLogDataService generatePlaneLogDataService;

    private HttpAirplaneService httpAirplaneService;

    @Autowired
    public ScheduledTasks(GeneratePlaneLogDataService generatePlaneLogDataService,
                          HttpAirplaneService httpAirplaneService) {
        this.generatePlaneLogDataService = generatePlaneLogDataService;
        this.httpAirplaneService = httpAirplaneService;
    }

    @Scheduled(fixedRate = 30000)
    public void generateFlightsData() {
        List<GetFlightDetailsDto> flights = httpAirplaneService.findCurrentFlights();
        LOGGER.debug("Simulator started.");

        if (flights != null && flights.size() > 0) {

            LOGGER.debug("Simulator starts generate flight details. Size: " + flights.size());
            try {
                List<PostFlightDetailsDto> postFlightDetailsDtos = new ArrayList<>();
                for (GetFlightDetailsDto dto : flights) {
                    PostFlightDetailsDto postFlightDetailsDto = generatePlaneLogDataService.createFlightDetails(dto);
                    postFlightDetailsDtos.add(postFlightDetailsDto);
                }
                httpAirplaneService.postCurrentPosition(postFlightDetailsDtos);
            }
            finally {
                LOGGER.debug("Simulator created and sent new data. Size: " + flights.size());
            }
        }
        else {
            LOGGER.debug("No flight routes found.");
        }
        LOGGER.debug("Simulator finished job.");
    }
}
