package com.gl.planesAndAirfileds.simulator.service;

import com.gl.planesAndAirfileds.simulator.domain.PostFlightDetailsDto;
import com.gl.planesAndAirfileds.simulator.domain.GetFlightDetailsDto;

import java.util.List;

/**
 * Created by marek.sobieraj on 2017-03-27.
 */
public interface HttpAirplaneService {
    List<GetFlightDetailsDto> findCurrentFlights();

    void postCurrentPosition(List<PostFlightDetailsDto> flightSimulateDtos);
}
