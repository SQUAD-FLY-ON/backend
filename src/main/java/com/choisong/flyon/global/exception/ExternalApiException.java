package com.choisong.flyon.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 외부 API 사용 시 발생하는 예외 상황을 다루는 최상위 예외입니다.
 */
@Getter
@RequiredArgsConstructor
public class ExternalApiException extends RuntimeException {

    private final ErrorCode errorCode;
}
