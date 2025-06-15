package com.choisong.flyon.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 검증 오류 발생 시 사용하는 최상위 예외입니다.,
 */
@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {

    private final ErrorCode errorCode;
}
