package com.choisong.flyon.weather.dto;

public record VilageForecastResponse(
    WeatherApiResponse<VilageForecastItem> response
) {

    public record VilageForecastItem(
        String baseDate,
        String baseTime,
        String category,
        String fcstDate,
        String fcstTime,
        String fcstValue,
        int nx,
        int ny
    ) {

    }
}
