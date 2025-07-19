package com.choisong.flyon.paraglidingspot.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Coordinate coordinate;
    private String phoneNumber;
    private String facilityName;
    private String websiteUrl;
    private String imgUrl;

    @Builder
    public ParaglidingSpot(String sido, String sigungu, String eupmyeondong, String fullAddress, final double latitude,
        final double longitude,
        final String phoneNumber,
        final String facilityName,
        final String websiteUrl, final String imgUrl) {
        this.address =
            Address.builder().eupmyeondong(eupmyeondong).fullAddress(fullAddress).sido(sido).sigungu(sigungu).build();
        this.coordinate = Coordinate.builder().latitude(latitude).longitude(longitude).build();
        this.phoneNumber = phoneNumber;
        this.facilityName = facilityName;
        this.websiteUrl = websiteUrl;
        this.imgUrl = imgUrl;
    }
}
