package com.choisong.flyon.flightlog.dto;

public record FlightLogRequest(
    String airfieldName,
    String airfieldImageUrl,
    double flightTime,
    double flightDistance,
    double averageSpeed,
    int flightAltitude
) {

}