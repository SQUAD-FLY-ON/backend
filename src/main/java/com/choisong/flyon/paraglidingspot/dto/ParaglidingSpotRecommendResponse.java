package com.choisong.flyon.paraglidingspot.dto;

public record ParaglidingSpotRecommendResponse(
    Long paraglidingSpotId,
    String spotName,
    String imgUrl,
    double starts
) {

}
