package com.choisong.flyon.flightlog.dto;

import java.time.LocalDateTime;

public record FlightLogResponse(
        String id,
        String airfieldName,
        double flightTime,
        double flightDistance,
        double averageSpeed,
        int flightAltitude,
        String videoUrl,
        LocalDateTime createdAt
) {}