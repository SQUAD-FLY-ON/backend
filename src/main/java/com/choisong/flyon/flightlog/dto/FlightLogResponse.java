package com.choisong.flyon.flightlog.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FlightLogResponse(
        String id,
        String airfieldName,
        double flightTime,
        double flightDistance,
        double averageSpeed,
        int flightAltitude,
        String videoUrl,
        LocalDateTime createdAt,
        List<TrackPointDto> track
) {}