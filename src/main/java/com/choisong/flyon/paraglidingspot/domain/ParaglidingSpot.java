package com.choisong.flyon.paraglidingspot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Entity
@Getter
@NoArgsConstructor
public class ParaglidingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;
    @Embedded
    private SpotCoordinate spotCoordinate;
    @Column(columnDefinition = "POINT SRID 4326", nullable = false)
    private Point point;
    private String phoneNumber;
    private String facilityName;
    private String websiteUrl;
    private String imgUrl;
    private String forecastCode;

    @Builder
    public ParaglidingSpot(String sido, String sigungu, String eupmyeondong, String fullAddress, final double latitude,
        final double longitude,
        final String phoneNumber,
        final String facilityName,
        final String websiteUrl, final String imgUrl, final String forecastCode) {
        this.forecastCode = forecastCode;
        this.address =
            Address.builder().eupmyeondong(eupmyeondong).fullAddress(fullAddress).sido(sido).sigungu(sigungu).build();
        this.spotCoordinate = SpotCoordinate.builder().latitude(latitude).longitude(longitude).build();
        this.phoneNumber = phoneNumber;
        this.facilityName = facilityName;
        this.websiteUrl = websiteUrl;
        this.imgUrl = imgUrl;
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
