package com.choisong.flyon.oauth.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;


public class OauthProviderNotFoundException extends BusinessException {

    public OauthProviderNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static OauthProviderNotFoundException providerNotFound() {
        return new OauthProviderNotFoundException(ErrorCode.PROVIDER_NOT_FOUND);
    }

    public static OauthProviderNotFoundException convertFailed() {
        return new OauthProviderNotFoundException(ErrorCode.CONVERTING_FAILED);
    }
}
