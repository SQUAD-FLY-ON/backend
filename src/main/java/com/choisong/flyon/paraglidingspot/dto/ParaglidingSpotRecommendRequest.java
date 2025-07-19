package com.choisong.flyon.paraglidingspot.dto;

public record ParaglidingSpotRecommendRequest(
    RecommendCriteria criteria,
    double latitude,
    double longitude
) {

}
