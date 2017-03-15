package com.gl.planesAndAirfileds.simulator.service.impl;

import com.gl.planesAndAirfileds.simulator.domain.FlightDetails;
import com.gl.planesAndAirfileds.simulator.domain.Plane;
import com.gl.planesAndAirfileds.simulator.domain.Point;
import com.gl.planesAndAirfileds.simulator.enums.FlightPhase;
import com.gl.planesAndAirfileds.simulator.service.GeneratePlaneLogDataService;
import com.gl.planesAndAirfileds.simulator.util.FuelConsumptionUtil;
import com.gl.planesAndAirfileds.simulator.util.PlaneDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by marcin.majka on 1/3/2017.
 */

@Service
public class GeneratePlaneLogDataServiceImpl implements GeneratePlaneLogDataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, FlightDetails> flightDetailsMap = new ConcurrentHashMap<>();

    // Speed in km/h
    private final static int MIN_SPEED = 4000;

    private final static int MAX_SPEED = 5000;

    @Value("${planeList.get.url}")
    private String planeListUrl;

    @Value("${flightDetails.post.url}")
    private String flightDetailsPostUrl = "http://localhost:8080/";

    private RestTemplate restTemplate;

    public GeneratePlaneLogDataServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public List<Plane> getListOfPlanes() {
        List<Plane> planeList = null;
        try {
            ResponseEntity<Plane[]> planeResponseEntity = restTemplate.getForEntity(planeListUrl, Plane[].class);
            if (planeResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                planeList = Arrays.asList(planeResponseEntity.getBody());
            }
            else {
                planeList = new ArrayList<>();
            }
        }
        catch (RestClientException e) {
            logger.error("Connection  exception " + e);
            planeList = new ArrayList<>();
        }

        return planeList;
    }

    @Override
    @Async
    public void generatePlaneDataLog(Plane plane) throws InterruptedException {
        FlightDetails flightDetails = flightDetailsMap.get(plane.getSid());
        if (flightDetails == null) {
            flightDetails = generateStartingFlightDetails(plane);
            flightDetailsMap.put(plane.getSid(), flightDetails);
        }
        else {
            if (flightDetails.getFlightPhase() != null && !flightDetails.getFlightPhase().equals(FlightPhase.LANDED)) {
                updateFlightDetails(flightDetails);

                try {
                    ResponseEntity<FlightDetails> responseEntity = restTemplate
                            .postForEntity(flightDetailsPostUrl, flightDetails, FlightDetails.class);
                }
                catch (RestClientException e) {
                    logger.error("Connection  exception " + e);
                }
            }

        }
        logger.info(flightDetails.toString());
    }

    @Override
    public void changePlainCourse(String sid, Double latitude, Double longitude) {

        if (sid != null && latitude != null && longitude != null) {
            FlightDetails flightDetails = flightDetailsMap.get(sid);
            if (flightDetails != null) {
                updateFlightDetails(flightDetails);
                Double currentLatitude = flightDetails.getGpsLatitude();
                Double currentLongitude = flightDetails.getGpsLongitude();
                if (currentLatitude != null && currentLongitude != null) {
                    currentLatitude = Math.toRadians(currentLatitude);
                    currentLongitude = Math.toRadians(currentLongitude);
                    latitude = Math.toRadians(latitude);
                    longitude = Math.toRadians(longitude);
                    double finalCourse = PlaneDataUtil
                            .calculateNewCourseRadians(currentLatitude, currentLongitude, latitude, longitude);
                    double distance = PlaneDataUtil
                            .calculateFlightDistanceBetweenPointsRadians(latitude, longitude, currentLatitude,
                                    currentLongitude);

                    flightDetails.setCourse(finalCourse);
                    flightDetails.setFlightDistance(flightDetails.getDistanceTraveled() + distance);
                    try {
                        ResponseEntity<FlightDetails> responseEntity = restTemplate
                                .postForEntity(flightDetailsPostUrl, flightDetails, FlightDetails.class);
                    }
                    catch (RestClientException e) {
                        logger.error("Connection  exception " + e);
                    }
                }

            }
        }
//
    }

    @Override
    public void updateFlightDetails(FlightDetails flightDetails) {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        long now = utc.toInstant().toEpochMilli();
        double flightTimeInHour = (now - flightDetails.getIncomingTime()) / 3600000d;
        double velocity = PlaneDataUtil
                .calculateCurrentVelocity(flightDetails.getFlightPhase(), flightDetails.getMaxVelocity());
        double distance = velocity * flightTimeInHour;
        flightDetails.setVelocity(velocity);
        Point destinationPoint = PlaneDataUtil
                .calculateDestinationPoint(flightDetails.getGpsLatitude(), flightDetails.getGpsLongitude(),
                        flightDetails.getCourse(), distance);
        flightDetails.updatePosition(destinationPoint);
        flightDetails.setIncomingTime(now);
        Double distanceTraveled = flightDetails.getDistanceTraveled();
        if (distanceTraveled == null) {
            distanceTraveled = distance;
        }
        else {
            distanceTraveled = distanceTraveled + distance;
        }

        flightDetails.setDistanceTraveled(distanceTraveled);
        FlightPhase flightPhase = PlaneDataUtil
                .calculateFlightPhaseChange(flightDetails.getFlightPhase(), flightDetails.getFlightStartTime(),
                        flightDetails.getDistanceTraveled(), flightDetails.getFlightDistance());
        flightDetails.setFlightPhase(flightPhase);
        double averageFuelConsumption = FuelConsumptionUtil
                .calculateCurrentFuelConsumption(flightDetails.getFlightPhase(),
                        flightDetails.getBasedFuelConsumption());
        flightDetails.setAverageFuelConsumption(averageFuelConsumption);
        LocalDateTime flightStartTime = flightDetails.getFlightStartTime();
        flightStartTime.toInstant(ZoneOffset.ofHours(0));
        flightDetails.setFlightTime(now - flightStartTime.toInstant(ZoneOffset.ofHours(0)).toEpochMilli());
        flightDetails.setRemainingFuel(FuelConsumptionUtil
                .calculateRemainingFuel(flightDetails.getRemainingFuel(), averageFuelConsumption, distance));

    }

    @Override
    public FlightDetails generateStartingFlightDetails(Plane plane) {
        Random random = new Random();
//        double latitude = random.doubles(-90, 90).limit(1).findFirst().getAsDouble();
//        double longitude = random.doubles(-180, 180).limit(1).findFirst().getAsDouble();
//        double latitude = random.doubles(49, 53).limit(1).findFirst().getAsDouble();
//        double longitude = random.doubles(15, 23).limit(1).findFirst().getAsDouble();
        double latitude = 52.45;
        double longitude = 20.651;

        // double course = random.doubles(0, 360).limit(1).findFirst().getAsDouble();
        double course = 30;
        double maxVelocity = random.doubles(MIN_SPEED, MAX_SPEED).limit(1).findFirst().getAsDouble();
        double velocity = PlaneDataUtil.calculateCurrentVelocity(FlightPhase.TAKE_OFF, maxVelocity);
        double fuelCapacityInLiter = random.doubles(100000, 250000).limit(1).findFirst().getAsDouble();
        double basedFuelConsumptionLiterPerKm = random.doubles(7, 16).limit(1).findFirst().getAsDouble();
        double averageFuelConsumptionLiterPerKm = FuelConsumptionUtil
                .calculateCurrentFuelConsumption(FlightPhase.TAKE_OFF, basedFuelConsumptionLiterPerKm);
        ;
        double distance = FuelConsumptionUtil
                .calculatePlaneDistanceInKm(basedFuelConsumptionLiterPerKm, fuelCapacityInLiter);
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);

        return new FlightDetails(utc.toInstant().toEpochMilli(), latitude, longitude, course, maxVelocity, velocity,
                plane, FlightPhase.TAKE_OFF, fuelCapacityInLiter, basedFuelConsumptionLiterPerKm,
                averageFuelConsumptionLiterPerKm, utc.toLocalDateTime(), distance);
    }
}
