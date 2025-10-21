package com.choisong.flyon.weather.infrastructure;

import com.choisong.flyon.weather.dto.MidTemperatureResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(
    url = "https://apihub.kma.go.kr/api/typ02/openApi/MidFcstInfoService",
    accept = MediaType.APPLICATION_JSON_VALUE
)
public interface MidTemperatureClient {

    @GetExchange("/getMidTa")
    MidTemperatureResponse getMidTemperatureForecast(
        @RequestParam("authKey") String serviceKey,
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("dataType") String dataType,
        @RequestParam("regId") String regId,
        @RequestParam("tmFc") String tmFc
    );
}