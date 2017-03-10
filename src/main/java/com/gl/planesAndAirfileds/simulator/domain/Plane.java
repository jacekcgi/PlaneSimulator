package com.gl.planesAndAirfileds.simulator.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Plane {

    private String  sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
         this.sid = sid;
    }
}
