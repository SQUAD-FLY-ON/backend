package com.choisong.flyon.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 비즈니스 로직 수행 중 발생하는 예외 상황을 다루기 위한 최상위 예외입니다.
 */
@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
}
