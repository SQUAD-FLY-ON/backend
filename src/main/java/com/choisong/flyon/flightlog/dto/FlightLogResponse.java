package com.choisong.flyon.flightlog.dto;

import java.time.LocalDateTime;
import java.util.List;

public record FlightLogResponse(
    String id,
    String airfieldName,
    String airfieldImageUrl,
    double flightTime,
    double flightDistance,
    double averageSpeed,
    int flightAltitude,
    LocalDateTime createdAt,
    List<TrackPointDto> track
) {

}