package com.choisong.flyon.global.generator;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FlightTrackCsvRow {
    @CsvBindByName(column = "id")
    private String id;

    @CsvBindByName(column = "paraglidingSpotId")
    private Long paraglidingSpotId;

    @CsvBindByName(column = "flightLogId")
    private String flightLogId;

    @CsvBindByName(column = "memberId")
    private Long memberId;

    @CsvBindByName(column = "updatedAt")
    private String updatedAt;

    @CsvBindByName(column = "points_json")
    private String pointsJson;
}
