package com.gl.planesAndAirfileds.simulator.service.impl;

import com.gl.planesAndAirfileds.simulator.domain.FakeGeneratedData;
import com.gl.planesAndAirfileds.simulator.domain.GetFlightDetailsDto;
import com.gl.planesAndAirfileds.simulator.domain.Point;
import com.gl.planesAndAirfileds.simulator.domain.PostFlightDetailsDto;
import com.gl.planesAndAirfileds.simulator.enums.FlightPhase;
import com.gl.planesAndAirfileds.simulator.exceptions.FlightRouteException;
import com.gl.planesAndAirfileds.simulator.service.GeneratePlaneLogDataService;
import com.gl.planesAndAirfileds.simulator.util.FuelConsumptionUtil;
import com.gl.planesAndAirfileds.simulator.util.PlaneDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by marcin.majka on 1/3/2017.
 */
@Service("generatePlaneLogDataService")
public class GeneratePlaneLogDataServiceImpl implements GeneratePlaneLogDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePlaneLogDataServiceImpl.class);

    private static final Random RANDOM = new Random();

    private Map<String, FakeGeneratedData> flightDetailsMap = new ConcurrentHashMap<>();

    // Speed in km/h
    private final static int MIN_SPEED = 400;

    private final static int MAX_SPEED = 900;

    @Override
//    @Async // na koniec, bo dane musza byc wylapane i wyslane razem !!!
    public PostFlightDetailsDto createFlightDetails(GetFlightDetailsDto getFlightDetailsDto) {
        FakeGeneratedData fakeGeneratedData = flightDetailsMap.computeIfAbsent(getFlightDetailsDto.getFlightRouteSid(),
                k -> generateFakeFlightData());
        if (getFlightDetailsDto.getLastFlightPhase() != null && getFlightDetailsDto
                .getLastFlightPhase() != FlightPhase.LANDED) {
            return generateFlightDetails(getFlightDetailsDto, fakeGeneratedData);
        }
        else {
            throw new FlightRouteException(
                    "Simulator does not generate data for FlightPhase status: " + getFlightDetailsDto
                            .getLastFlightPhase());
        }

    }

    private PostFlightDetailsDto generateFlightDetails(GetFlightDetailsDto flightDetailsDto,
                                                       FakeGeneratedData fakeGeneratedData) {
        PostFlightDetailsDto postFlightDetailsDto = new PostFlightDetailsDto();
        LOGGER.debug("Start generate new flight details for: " + flightDetailsDto.getFlightRouteSid());
//        cal velocity
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        double flightTimeInHour = (now - flightDetailsDto.getLastCreatedDate()
                .toEpochSecond(ZoneOffset.UTC)) / 3600000d;
        double velocity = PlaneDataUtil
                .calculateCurrentVelocity(flightDetailsDto.getLastFlightPhase(), fakeGeneratedData.getMaxVelocity());
        double distance = velocity * flightTimeInHour;
        postFlightDetailsDto.setVelocity(velocity);

//        calc new position
        Point newCurrentPosition = PlaneDataUtil
                .calculateDestinationPoint(flightDetailsDto.getCurrentLatitude(),
                        flightDetailsDto.getCurrentLongitude(),
                        calculateCourse(flightDetailsDto), distance);
        postFlightDetailsDto.setGpsLatitude(newCurrentPosition.getLatitude());
        postFlightDetailsDto.setGpsLongitude(newCurrentPosition.getLongitude());

//        new distance traveled
        double distanceTraveled = flightDetailsDto.getDistanceTraveled();
        distanceTraveled += distance;
        postFlightDetailsDto.setDistanceTraveled(distanceTraveled);

//        calc new flight phase
        FlightPhase newFlightPhase = PlaneDataUtil
                .calculateFlightPhaseChange(flightDetailsDto.getLastFlightPhase(),
                        flightDetailsDto.getStartFlightDate(),
                        distanceTraveled, flightDetailsDto.getFlightDistance());
        postFlightDetailsDto.setFlightPhase(newFlightPhase);

//        calc fuel consumption
        double averageFuelConsumption = FuelConsumptionUtil
                .calculateCurrentFuelConsumption(newFlightPhase, fakeGeneratedData.getBasedFuelConsumption());
        double remainingFuel = FuelConsumptionUtil
                .calculateRemainingFuel(averageFuelConsumption, averageFuelConsumption, distance);
        postFlightDetailsDto.setAverageFuelConsumption(averageFuelConsumption);
        postFlightDetailsDto.setRemainingFuel(remainingFuel);
//        sid
        postFlightDetailsDto.setFlightRouteSid(flightDetailsDto.getFlightRouteSid());

        LOGGER.debug("Generated new Flight details: " + postFlightDetailsDto);
        return postFlightDetailsDto;
    }

    private double calculateCourse(GetFlightDetailsDto getFlightDetailsDto) {

        double currentLatitude = getFlightDetailsDto.getCurrentLatitude();
        double currentLongitude = getFlightDetailsDto.getCurrentLongitude();
        currentLatitude = Math.toRadians(currentLatitude);
        currentLongitude = Math.toRadians(currentLongitude);
        double destinationLatitude = Math.toRadians(getFlightDetailsDto.getDestinationLatitude());
        double destinationLongitude = Math.toRadians(getFlightDetailsDto.getDestinationLongitude());
        return PlaneDataUtil
                .calculateNewCourseRadians(currentLatitude, currentLongitude, destinationLatitude,
                        destinationLongitude);
    }

    @Override
    public FakeGeneratedData generateFakeFlightData() {
        FakeGeneratedData fakeGeneratedData = new FakeGeneratedData();
        double maxVelocity = RANDOM.doubles(MIN_SPEED, MAX_SPEED).limit(1).findFirst().getAsDouble();
        double basedFuelConsumption = RANDOM.doubles(7, 16).limit(1).findFirst().getAsDouble();
        fakeGeneratedData.setMaxVelocity(maxVelocity);
        fakeGeneratedData.setBasedFuelConsumption(basedFuelConsumption);
        return fakeGeneratedData;
//        double latitude = RANDOM.doubles(-90, 90).limit(1).findFirst().getAsDouble();
//        double longitude = RANDOM.doubles(-180, 180).limit(1).findFirst().getAsDouble();
//        double latitude = RANDOM.doubles(49, 53).limit(1).findFirst().getAsDouble();
//        double longitude = RANDOM.doubles(15, 23).limit(1).findFirst().getAsDouble();
//        double course = RANDOM.doubles(0, 360).limit(1).findFirst().getAsDouble();
//
//        double velocity = PlaneDataUtil.calculateCurrentVelocity(FlightPhase.TAKE_OFF, maxVelocity);
//        double fuelCapacityInLiter = RANDOM.doubles(100000, 250000).limit(1).findFirst().getAsDouble();
//
//        double averageFuelConsumptionLiterPerKm = FuelConsumptionUtil
//                .calculateCurrentFuelConsumption(FlightPhase.TAKE_OFF, basedFuelConsumptionLiterPerKm);
//        double distance = FuelConsumptionUtil
//                .calculatePlaneDistanceInKm(basedFuelConsumptionLiterPerKm, fuelCapacityInLiter);
//        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
    }

}
