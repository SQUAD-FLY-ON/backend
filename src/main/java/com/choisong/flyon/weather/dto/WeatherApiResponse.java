package com.choisong.flyon.weather.dto;

import java.util.List;

public record WeatherApiResponse<T>(
    ResponseHeader header,
    WeatherApiBody<T> body
) {

    public record ResponseHeader(
        String resultCode,
        String resultMsg
    ) {

    }

    public record WeatherApiBody<T>(
        String dataType,
        WeatherApiItems<T> items,
        int numOfRows,
        int pageNo,
        int totalCount
    ) {

    }

    public record WeatherApiItems<T>(
        List<T> item
    ) {

    }
}