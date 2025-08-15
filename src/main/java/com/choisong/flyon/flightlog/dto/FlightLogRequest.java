package com.choisong.flyon.flightlog.dto;

public record FlightLogRequest(
        String airfieldName,
        double flightTime,
        double flightDistance,
        double averageSpeed,
        int jumpAltitude,
        String videoUrl
) {}