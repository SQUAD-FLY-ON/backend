package com.choisong.flyon.auth.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ValidationException;

public class PasswordNotMatchedException extends ValidationException {

    public PasswordNotMatchedException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static PasswordNotMatchedException notMatched() {
        return new PasswordNotMatchedException(ErrorCode.PASSWORD_NOT_MATCHED);
    }
}
