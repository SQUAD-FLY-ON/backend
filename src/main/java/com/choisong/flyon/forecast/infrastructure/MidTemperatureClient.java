package com.choisong.flyon.forecast.infrastructure;

import com.choisong.flyon.forecast.dto.MidTemperatureResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

public interface MidTemperatureClient {

    @GetExchange(" https://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa")
    MidTemperatureResponse getMidTemperatureForecast(
        @RequestParam("serviceKey") String serviceKey,
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("dataType") String dataType,
        @RequestParam("regId") String regId,
        @RequestParam("tmFc") String tmFc
    );
}