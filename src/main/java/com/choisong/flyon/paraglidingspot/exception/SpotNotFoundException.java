package com.choisong.flyon.paraglidingspot.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class SpotNotFoundException extends BusinessException {

    public SpotNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static SpotNotFoundException notFound() {
        return new SpotNotFoundException(ErrorCode.SPOT_NOT_FOUND);
    }
}
