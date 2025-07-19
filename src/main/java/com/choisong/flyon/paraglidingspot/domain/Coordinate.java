package com.choisong.flyon.paraglidingspot.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Coordinate {

    private double latitude;
    private double longitude;

    @Builder
    public Coordinate(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
