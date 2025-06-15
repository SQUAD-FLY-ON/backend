package com.choisong.flyon.jwt.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class RefreshTokenNotFoundException extends BusinessException {

    public RefreshTokenNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static RefreshTokenNotFoundException tokenNotFound(){
        return new RefreshTokenNotFoundException(ErrorCode.REFRESHTOKEN_NOT_FOUND);
    }

    public static RefreshTokenNotFoundException tokenCookieNotFound(){
        return new RefreshTokenNotFoundException(ErrorCode.REFRESHTOKEN_COOKIE_NOT_FOUND);
    }
}
