package com.choisong.flyon.weather.infrastructure;

import com.choisong.flyon.weather.dto.MidLandForecastResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(
    url = "https://apis.data.go.kr/1360000/MidFcstInfoService",
    accept = MediaType.APPLICATION_JSON_VALUE
)
public interface MidLandForecastClient {

    @GetExchange("/getMidLandFcst")
    MidLandForecastResponse getMidLandForecast(
        @RequestParam("serviceKey") String serviceKey,
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("dataType") String dataType,
        @RequestParam("regId") String regId,
        @RequestParam("tmFc") String tmFc
    );
}