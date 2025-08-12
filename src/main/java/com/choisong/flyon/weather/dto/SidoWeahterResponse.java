package com.choisong.flyon.weather.dto;

import java.util.List;

public record SidoWeahterResponse(
    List<SigunguWeather> weatherInfos
) {

    public record SigunguWeather(
        String sigungu,
        List<DailyWeather> dailyWeathers
    ) {

    }

    public record DailyWeather(
        String monthDate,
        String maxTemp,
        String minTemp,
        String sky
    ) {

    }

}
