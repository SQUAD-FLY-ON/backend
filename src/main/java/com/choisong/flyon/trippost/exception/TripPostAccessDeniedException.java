package com.choisong.flyon.trippost.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class TripPostAccessDeniedException extends BusinessException {

    private TripPostAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static TripPostAccessDeniedException accessDenied() {
        return new TripPostAccessDeniedException(ErrorCode.TRIP_POST_ACCESS_DENIED);
    }
}