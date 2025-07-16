package com.choisong.flyon.paraglidingspot.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    // 시/도
    private String sido;
    // 시/구/군
    private String sigungu;
    // 읍/면/동
    private String eupmyeon;
    // 리
    private String ri;
    // 도로명 주소
    private String street;
}
