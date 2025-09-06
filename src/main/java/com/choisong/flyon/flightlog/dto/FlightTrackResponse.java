package com.choisong.flyon.flightlog.dto;

import java.util.List;

public record FlightTrackResponse(
    List<TrackPointDto> points
) {

}
