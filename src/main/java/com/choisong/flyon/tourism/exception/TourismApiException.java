package com.choisong.flyon.tourism.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ExternalApiException;

public class TourismApiException extends ExternalApiException {

    private TourismApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static TourismApiException callFailed() {
        return new TourismApiException(ErrorCode.TOURISM_API_ERROR);
    }

    public static TourismApiException badResponse() {
        return new TourismApiException(ErrorCode.TOURISM_API_BAD_RESPONSE);
    }
}