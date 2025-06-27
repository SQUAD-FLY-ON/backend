package com.choisong.flyon.trippost.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class TripPostNotFoundException extends BusinessException {

    private TripPostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static TripPostNotFoundException tripNotFound() {
        return new TripPostNotFoundException(ErrorCode.TRIP_POST_NOT_FOUND);
    }
}