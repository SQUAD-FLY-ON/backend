package com.choisong.flyon.flightlog.dto;

import java.time.LocalDateTime;

public record TrackPointDto(
    double latitude,
    double longitude,
    double altitude,
    LocalDateTime time
) {

}