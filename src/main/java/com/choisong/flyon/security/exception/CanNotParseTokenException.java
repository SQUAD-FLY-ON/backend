package com.choisong.flyon.security.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.SecurityException;

public class CanNotParseTokenException extends SecurityException {

    public CanNotParseTokenException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static CanNotParseTokenException canNotParse() {
        return new CanNotParseTokenException(ErrorCode.CAN_NOT_PARSE_TOKEN);
    }
}
