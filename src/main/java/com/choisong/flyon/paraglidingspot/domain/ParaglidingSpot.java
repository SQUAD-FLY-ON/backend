package com.choisong.flyon.paraglidingspot.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String spotName;
    private String websiteUrl;
    private String imgUrl;
}
