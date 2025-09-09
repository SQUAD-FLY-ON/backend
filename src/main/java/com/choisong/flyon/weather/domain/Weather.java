package com.choisong.flyon.weather.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate // 변경된 필드만 UPDATE
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version // 낙관적 락으로 동시 업데이트 충돌 방지
    private Long version;

    private String sido;
    private String sigungu;

    private String midTempCode;
    private String midWeatherCode;
    private int x;
    private int y;

    @Setter private String weather1;  // 오늘 (단기)
    @Setter private String weather2;  // 내일 (단기)
    @Setter private String weather3;  // 모레 (단기)

    private String weather4;          // D+4 (중기)
    private String weather5;
    private String weather6;
    private String weather7;
    private String weather8;
    private String weather9;
    private String weather10;

    @Setter private String temp1Max;  // (단기)
    @Setter private String temp1Min;
    @Setter private String temp2Max;
    @Setter private String temp2Min;
    @Setter private String temp3Max;
    @Setter private String temp3Min;

    private String temp4Max;          // (중기)
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

    /** 기존 setter: 전체 덮어씀 (단, 내부적으로만 사용할 수 있게 두고싶으면 private로 둬도 됨) */
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

    /** ★ 추천: null/blank는 무시하여 기존값 보존 (중기) */
    public void setMidWeatherNullable(
        String w4, String w5, String w6, String w7, String w8, String w9, String w10
    ) {
        if (nonEmpty(w4))  this.weather4  = w4;
        if (nonEmpty(w5))  this.weather5  = w5;
        if (nonEmpty(w6))  this.weather6  = w6;
        if (nonEmpty(w7))  this.weather7  = w7;
        if (nonEmpty(w8))  this.weather8  = w8;
        if (nonEmpty(w9))  this.weather9  = w9;
        if (nonEmpty(w10)) this.weather10 = w10;
    }

    /** ★ 추천: null은 무시하여 기존값 보존 (중기) */
    public void setMidTempNullable(
        String t4Min, String t4Max,
        String t5Min, String t5Max,
        String t6Min, String t6Max,
        String t7Min, String t7Max,
        String t8Min, String t8Max,
        String t9Min, String t9Max,
        String t10Min, String t10Max
    ) {
        if (t4Min  != null) this.temp4Min  = t4Min;
        if (t4Max  != null) this.temp4Max  = t4Max;
        if (t5Min  != null) this.temp5Min  = t5Min;
        if (t5Max  != null) this.temp5Max  = t5Max;
        if (t6Min  != null) this.temp6Min  = t6Min;
        if (t6Max  != null) this.temp6Max  = t6Max;
        if (t7Min  != null) this.temp7Min  = t7Min;
        if (t7Max  != null) this.temp7Max  = t7Max;
        if (t8Min  != null) this.temp8Min  = t8Min;
        if (t8Max  != null) this.temp8Max  = t8Max;
        if (t9Min  != null) this.temp9Min  = t9Min;
        if (t9Max  != null) this.temp9Max  = t9Max;
        if (t10Min != null) this.temp10Min = t10Min;
        if (t10Max != null) this.temp10Max = t10Max;
    }

    private boolean nonEmpty(String s) { return s != null && !s.isBlank(); }

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
