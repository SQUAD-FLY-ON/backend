package com.choisong.flyon.flightlog.dto;

import java.util.List;

public record FlightLogRequest(
    String airfieldName,
    double flightTime,
    double flightDistance,
    double averageSpeed,
    int flightAltitude
) {

}