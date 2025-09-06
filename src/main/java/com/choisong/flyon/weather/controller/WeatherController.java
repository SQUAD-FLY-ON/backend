package com.choisong.flyon.weather.controller;

import com.choisong.flyon.weather.dto.SidoWeahterResponse;
import com.choisong.flyon.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/weather")
@RequiredArgsConstructor
@RestController
@Tag(name = "지역별 날씨")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    @Operation(summary = "지역별 1-10일간 날씨 정보", description = "시/도 문자열을 입력받아 군/구 단위로 날씨를 반환합니다.")
    public SidoWeahterResponse getVilageForecast(
        @RequestParam String sido,
        @RequestParam String tripStart,
        @RequestParam String tripEnd
    ) {
        return weatherService.getSidoWeatherResponse(sido, tripStart, tripEnd);
    }
}
