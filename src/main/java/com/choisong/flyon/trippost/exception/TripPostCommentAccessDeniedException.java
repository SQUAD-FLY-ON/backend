package com.choisong.flyon.trippost.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class TripPostCommentAccessDeniedException extends BusinessException {

    private TripPostCommentAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static TripPostCommentAccessDeniedException accessDenied() {
        return new TripPostCommentAccessDeniedException(ErrorCode.TRIP_POST_COMMENT_ACCESS_DENIED);
    }
}