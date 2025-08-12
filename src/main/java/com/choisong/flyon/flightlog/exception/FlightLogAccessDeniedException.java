package com.choisong.flyon.flightlog.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class FlightLogAccessDeniedException extends BusinessException {

    private FlightLogAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static FlightLogAccessDeniedException accessDenied() {
        return new FlightLogAccessDeniedException(ErrorCode.FLIGHT_LOG_ACCESS_DENIED);
    }
}