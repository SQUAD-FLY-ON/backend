package com.choisong.flyon.member.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ValidationException;

public class LoginIdDuplicatedException extends ValidationException {

    public LoginIdDuplicatedException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static LoginIdDuplicatedException duplicated() {
        return new LoginIdDuplicatedException(ErrorCode.LOGIN_ID_DUPLICATED);
    }
}
