package com.choisong.flyon.paraglidingspot.dto;

import java.util.List;

public record SearchedSpotResponse(
    List<SearchedSpot> searchedSpots
) {

    public record SearchedSpot(
        String id,
        String name,
        String fullAddress,
        String imgUrl,
        double latitude,
        double longitude
    ) {

    }
}
