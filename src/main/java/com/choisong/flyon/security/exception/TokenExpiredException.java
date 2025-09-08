package com.choisong.flyon.security.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.SecurityException;

public class TokenExpiredException extends SecurityException {

    public TokenExpiredException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static TokenExpiredException accessTokenExpired() {
        return new TokenExpiredException(ErrorCode.ACCESSTOKEN_EXPIRED);
    }

    public static TokenExpiredException refreshTokenExpired() {
        return new TokenExpiredException(ErrorCode.REFRESHTOKEN_EXPIRED);
    }
}
