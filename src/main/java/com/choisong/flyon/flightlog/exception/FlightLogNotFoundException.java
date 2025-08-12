package com.choisong.flyon.flightlog.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class FlightLogNotFoundException extends BusinessException {

    private FlightLogNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static FlightLogNotFoundException notFound() {
        return new FlightLogNotFoundException(ErrorCode.FLIGHT_LOG_NOT_FOUND);
    }
}