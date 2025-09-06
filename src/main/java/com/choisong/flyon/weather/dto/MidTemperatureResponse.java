package com.choisong.flyon.weather.dto;

public record MidTemperatureResponse(
    WeatherApiResponse<MidTemperatureItem> response
) {

    public record MidTemperatureItem(
        String regId,
        String taMin4, String taMin4Low, String taMin4High,
        String taMax4, String taMax4Low, String taMax4High,
        String taMin5, String taMin5Low, String taMin5High,
        String taMax5, String taMax5Low, String taMax5High,
        String taMin6, String taMin6Low, String taMin6High,
        String taMax6, String taMax6Low, String taMax6High,
        String taMin7, String taMin7Low, String taMin7High,
        String taMax7, String taMax7Low, String taMax7High,
        String taMin8, String taMin8Low, String taMin8High,
        String taMax8, String taMax8Low, String taMax8High,
        String taMin9, String taMin9Low, String taMin9High,
        String taMax9, String taMax9Low, String taMax9High,
        String taMin10, String taMin10Low, String taMin10High,
        String taMax10, String taMax10Low, String taMax10High
    ) {

    }
}
