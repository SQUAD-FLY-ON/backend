package com.choisong.flyon.paraglidingspot.dto;

public record RecommendSpot(
    Long id,
    String spotName,
    String imgUrl,
    String fullAddress,
    String sido,
    double latitude,
    double longitude
) {

}
