package com.gl.planesAndAirfileds.simulator.service.impl;

import com.gl.planesAndAirfileds.simulator.domain.PostFlightDetailsDto;
import com.gl.planesAndAirfileds.simulator.domain.GetFlightDetailsDto;
import com.gl.planesAndAirfileds.simulator.service.HttpAirplaneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by marek.sobieraj on 2017-03-27.
 */
@Service("httpAirplaneService")
public class HttpAirplaneServiceImpl implements HttpAirplaneService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpAirplaneServiceImpl.class);

    @Value("${currentFlights.get.url}")
    private String currentFlightsUrl;

    @Value("${flightDetails.post.url}")
    private String flightDetailsPostUrl;

    private RestTemplate restTemplate;

    private RestTemplateBuilder builder;

    @Autowired
    public HttpAirplaneServiceImpl(RestTemplateBuilder builder) {
        this.builder = builder;
    }

    @Override
    public List<GetFlightDetailsDto> findCurrentFlights() {
        ResponseEntity<GetFlightDetailsDto[]> planeResponseEntity = restTemplate
                .getForEntity(currentFlightsUrl, GetFlightDetailsDto[].class);
        if (planeResponseEntity.getBody().length > 0) {
            return Arrays.asList(planeResponseEntity.getBody());
        }
        return Collections.emptyList();
    }

    @Override
    public void postCurrentPosition(List<PostFlightDetailsDto> flightSimulateDtos) {
        ResponseEntity<PostFlightDetailsDto> responseEntity = restTemplate
                .postForEntity(flightDetailsPostUrl, flightSimulateDtos, PostFlightDetailsDto.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            LOGGER.error("Send calculated data to server failed", responseEntity);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(builder, "RestTemplateBuilder not set");
        restTemplate = builder.build();
    }
}
