package com.choisong.flyon.trippost.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class TripPostCommentNotFoundException extends BusinessException {

    private TripPostCommentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static TripPostCommentNotFoundException notFound() {
        return new TripPostCommentNotFoundException(ErrorCode.TRIP_POST_COMMENT_NOT_FOUND);
    }
}
