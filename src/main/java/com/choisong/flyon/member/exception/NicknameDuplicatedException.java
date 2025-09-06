package com.choisong.flyon.member.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ValidationException;

public class NicknameDuplicatedException extends ValidationException {

    public NicknameDuplicatedException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static NicknameDuplicatedException duplicated() {
        return new NicknameDuplicatedException(ErrorCode.NICKNAME_DUPLICATED);
    }
}
