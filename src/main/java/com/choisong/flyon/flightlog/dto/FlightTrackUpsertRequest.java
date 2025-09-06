package com.choisong.flyon.flightlog.dto;

import java.util.List;

public record FlightTrackUpsertRequest(
    List<TrackPointDto> points
) {

}