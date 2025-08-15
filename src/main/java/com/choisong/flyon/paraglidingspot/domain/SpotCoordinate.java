package com.choisong.flyon.paraglidingspot.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class SpotCoordinate {

    private double latitude;
    private double longitude;

    @Builder
    public SpotCoordinate(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
