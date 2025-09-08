package com.choisong.flyon.global.generator;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class ParaglidingSpotCsv {

    @CsvBindByName(column = "facilityName")
    private String facilityName;

    @CsvBindByName(column = "sido")
    private String sido;

    @CsvBindByName(column = "sigungu")
    private String sigungu;

    @CsvBindByName(column = "eupmyondong")
    private String eupmyondong;

    @CsvBindByName(column = "fullAddress")
    private String fullAddress;

    @CsvBindByName(column = "latitude")
    private Double latitude;

    @CsvBindByName(column = "longitude")
    private Double longitude;

    @CsvBindByName(column = "phoneNumber")
    private String phoneNumber;

    @CsvBindByName(column = "websiteUrl")
    private String websiteUrl;

    @CsvBindByName(column = "midTempCode")
    private String midTempCode;

    @CsvBindByName(column = "midWeatherCode")
    private String midWeatherCode;
    @CsvBindByName(column = "x")
    private Double x;
    @CsvBindByName(column = "y")
    private Double y;
    @CsvBindByName(column = "imgUrl")
    private String imgUrl;
}
