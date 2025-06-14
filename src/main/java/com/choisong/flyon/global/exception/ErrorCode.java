package com.choisong.flyon.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    /** Global Error 0~99 */
    AUTHORITY_NOT_VALID(HttpStatus.FORBIDDEN, "000", "접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String msg;
}
