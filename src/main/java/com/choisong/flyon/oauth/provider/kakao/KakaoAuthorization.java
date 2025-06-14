package com.choisong.flyon.oauth.provider.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoAuthorization(
    String tokenType,
    String accessToken,
    String idToken,
    Integer expiresIn,
    String refreshToken,
    Integer refreshTokenExpiresIn,
    String scope) {

}
