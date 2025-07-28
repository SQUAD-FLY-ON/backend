package com.choisong.flyon.forecast.infrastructure;

import com.choisong.flyon.forecast.dto.MidLandForecastResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

public interface MidLandForecastClient {

    @GetExchange(" https://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst")
    MidLandForecastResponse getMidLandForecast(
        @RequestParam("serviceKey") String serviceKey,
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("dataType") String dataType,
        @RequestParam("regId") String regId,
        @RequestParam("tmFc") String tmFc
    );
}