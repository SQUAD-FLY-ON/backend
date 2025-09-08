package com.choisong.flyon.weather.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sido;
    private String sigungu;

    private String midTempCode;
    private String midWeatherCode;
    private int x;
    private int y;

    @Setter private String weather1;  // 오늘
    @Setter private String weather2;  // 내일
    @Setter private String weather3;  // 모레

    private String weather4;          // D+4
    private String weather5;
    private String weather6;
    private String weather7;
    private String weather8;
    private String weather9;
    private String weather10;

    @Setter private String temp1Max;
    @Setter private String temp1Min;
    @Setter private String temp2Max;
    @Setter private String temp2Min;
    @Setter private String temp3Max;
    @Setter private String temp3Min;

    private String temp4Max;
    private String temp4Min;
    private String temp5Max;
    private String temp5Min;
    private String temp6Max;
    private String temp6Min;
    private String temp7Max;
    private String temp7Min;
    private String temp8Max;
    private String temp8Min;
    private String temp9Max;
    private String temp9Min;
    private String temp10Max;
    private String temp10Min;

    public Weather(final String sido, final String sigungu, final String midTempCode, final String midWeatherCode,
        final int x, final int y) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.midTempCode = midTempCode;
        this.midWeatherCode = midWeatherCode;
        this.x = x;
        this.y = y;
    }

    public void setMidTemp(
        String temp4Min, String temp4Max,
        String temp5Min, String temp5Max,
        String temp6Min, String temp6Max,
        String temp7Min, String temp7Max,
        String temp8Min, String temp8Max,
        String temp9Min, String temp9Max,
        String temp10Min, String temp10Max
    ) {
        this.temp4Min = temp4Min;
        this.temp4Max = temp4Max;
        this.temp5Min = temp5Min;
        this.temp5Max = temp5Max;
        this.temp6Min = temp6Min;
        this.temp6Max = temp6Max;
        this.temp7Min = temp7Min;
        this.temp7Max = temp7Max;
        this.temp8Min = temp8Min;
        this.temp8Max = temp8Max;
        this.temp9Min = temp9Min;
        this.temp9Max = temp9Max;
        this.temp10Min = temp10Min;
        this.temp10Max = temp10Max;
    }

    public void setMidWeather(
        String weather4, String weather5, String weather6,
        String weather7, String weather8, String weather9,
        String weather10
    ) {
        this.weather4 = weather4;
        this.weather5 = weather5;
        this.weather6 = weather6;
        this.weather7 = weather7;
        this.weather8 = weather8;
        this.weather9 = weather9;
        this.weather10 = weather10;
    }

    public boolean isGoodWeatherExist() {
        return "맑음".equals(weather1) ||
            "맑음".equals(weather2) ||
            "맑음".equals(weather3) ||
            "맑음".equals(weather4) ||
            "맑음".equals(weather5) ||
            "맑음".equals(weather6) ||
            "맑음".equals(weather7) ||
            "맑음".equals(weather8) ||
            "맑음".equals(weather9) ||
            "맑음".equals(weather10);
    }
}
