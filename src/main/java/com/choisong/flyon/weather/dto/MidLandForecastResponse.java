package com.choisong.flyon.weather.dto;

public record MidLandForecastResponse(
    WeatherApiResponse<MidLandForecastItem> response
) {

    public record MidLandForecastItem(
        String regId,
        String rnSt4Am,
        String rnSt4Pm,
        String rnSt5Am,
        String rnSt5Pm,
        String rnSt6Am,
        String rnSt6Pm,
        String rnSt7Am,
        String rnSt7Pm,
        String rnSt8,
        String rnSt9,
        String rnSt10,
        String wf4Am,
        String wf4Pm,
        String wf5Am,
        String wf5Pm,
        String wf6Am,
        String wf6Pm,
        String wf7Am,
        String wf7Pm,
        String wf8,
        String wf9,
        String wf10
    ) {

    }
}