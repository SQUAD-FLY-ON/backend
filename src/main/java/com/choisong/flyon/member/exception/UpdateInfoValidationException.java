package com.choisong.flyon.member.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ValidationException;

public class UpdateInfoValidationException extends ValidationException {

    public UpdateInfoValidationException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
