package com.choisong.flyon.auth.dto;

public record LoginRequest(
    String loginId,
    String password
) {

}
