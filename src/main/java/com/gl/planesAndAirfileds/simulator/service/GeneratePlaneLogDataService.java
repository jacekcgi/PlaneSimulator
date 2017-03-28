package com.gl.planesAndAirfileds.simulator.service;

import com.gl.planesAndAirfileds.simulator.domain.FakeGeneratedData;
import com.gl.planesAndAirfileds.simulator.domain.PostFlightDetailsDto;
import com.gl.planesAndAirfileds.simulator.domain.GetFlightDetailsDto;

/**
 * Created by marcin.majka on 1/3/2017.
 */

public interface GeneratePlaneLogDataService {

    PostFlightDetailsDto createFlightDetails(GetFlightDetailsDto getFlightDetailsDto);

    FakeGeneratedData generateFakeFlightData();
}
