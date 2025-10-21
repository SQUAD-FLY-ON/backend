package com.choisong.flyon.weather.infrastructure;

import com.choisong.flyon.weather.dto.VilageForecastResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(
    url = "https://apihub.kma.go.kr/api/typ02/openApi/VilageFcstInfoService_2.0",
    accept = MediaType.APPLICATION_JSON_VALUE
)
public interface VilageForecastClient {

    @GetExchange("/getVilageFcst")
    VilageForecastResponse getVilageForecast(
        @RequestParam("authKey") String serviceKey,
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("dataType") String dataType,
        @RequestParam("base_date") String baseDate,
        @RequestParam("base_time") String baseTime,
        @RequestParam("nx") int nx,
        @RequestParam("ny") int ny
    );

}
