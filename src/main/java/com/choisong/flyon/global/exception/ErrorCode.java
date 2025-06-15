package com.choisong.flyon.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * Auth Error 0~99
     */
    AUTHORITY_NOT_VALID(HttpStatus.FORBIDDEN, "000", "접근 권한이 없습니다."),
    PROVIDER_NOT_FOUND(HttpStatus.NOT_FOUND,"001","지원하지 않는 소셜로그인 공급자입니다."),
    CONVERTING_FAILED(HttpStatus.BAD_REQUEST,"002","요청한 소셜로그인 공급자를 변환할 수 없습니다."),
    REFRESHTOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"003","리프레쉬 토큰을 찾을 수 없습니다"),
    REFRESHTOKEN_COOKIE_NOT_FOUND(HttpStatus.NOT_FOUND,"004","리프레쉬 토큰을 쿠키에서 찾을 수 없습니다."),

    /**
     * Member Error 100~199
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"100","회원 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}
