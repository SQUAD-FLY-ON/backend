package com.choisong.flyon.paraglidingspot.dto;

public record SearchBoxRequest(
    double centerLatitude,
    double centerLongitude,
    double cornerLatitude,
    double cornerLongitude
) {

}
