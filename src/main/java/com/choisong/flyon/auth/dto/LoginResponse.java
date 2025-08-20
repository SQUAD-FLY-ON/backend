package com.choisong.flyon.auth.dto;

public record LoginResponse(
    MemberInfo memberInfo,
    String accessToken,
    String refreshToken
) {

}
