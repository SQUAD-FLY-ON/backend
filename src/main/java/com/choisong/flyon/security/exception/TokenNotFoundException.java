package com.choisong.flyon.security.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.SecurityException;

public class TokenNotFoundException extends SecurityException {

    public TokenNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static TokenNotFoundException refreshTokenNotFound() {
        return new TokenNotFoundException(ErrorCode.REFRESHTOKEN_NOT_FOUND);
    }

    public static TokenNotFoundException refreshTokenCookieNotFound() {
        return new TokenNotFoundException(ErrorCode.REFRESHTOKEN_COOKIE_NOT_FOUND);
    }

    public static TokenNotFoundException accessTokenHeaderNotFound() {
        return new TokenNotFoundException(ErrorCode.ACCESSTOKEN_HEADER_NOT_FOUND);
    }
}
