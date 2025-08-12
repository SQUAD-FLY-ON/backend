package com.choisong.flyon.trippost.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class TripPostLikeNotFoundException extends BusinessException {

    private TripPostLikeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static TripPostLikeNotFoundException likeNotFound() {
        return new TripPostLikeNotFoundException(ErrorCode.TRIP_POST_LIKE_NOT_FOUND);
    }
}