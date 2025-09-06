package com.choisong.flyon.paraglidingspot.service;

import com.choisong.flyon.paraglidingspot.dto.SearchBoxRequest;

public class DistanceCalculator {

    private static final double EARTH_RADIUS_M = 6_371_000;

    public static int haversineDistanceMeters(SearchBoxRequest request) {
        double lat1Rad = Math.toRadians(request.centerLatitude());
        double lon1Rad = Math.toRadians(request.centerLongitude());
        double lat2Rad = Math.toRadians(request.cornerLatitude());
        double lon2Rad = Math.toRadians(request.cornerLongitude());

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(dLat / 2), 2)
            + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_M * c;

        return (int) distance;
    }
}
