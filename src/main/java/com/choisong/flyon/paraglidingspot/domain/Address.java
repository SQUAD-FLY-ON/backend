package com.choisong.flyon.paraglidingspot.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    // 시/도
    private String sido;
    // 시/구/군
    private String sigungu;
    // 읍/면/동
    private String eupmyeondong;
    // 도로명 주소
    private String fullAddress;

    @Builder
    public Address(final String sido, final String sigungu, final String eupmyeondong, final String fullAddress) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
        this.fullAddress = fullAddress;
    }
}
