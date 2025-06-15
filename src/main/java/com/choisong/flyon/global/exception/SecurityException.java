package com.choisong.flyon.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SecurityException extends RuntimeException {

    private final ErrorCode errorCode;
}
