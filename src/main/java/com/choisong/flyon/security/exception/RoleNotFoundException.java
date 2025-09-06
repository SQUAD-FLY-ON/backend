package com.choisong.flyon.security.exception;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.SecurityException;

public class RoleNotFoundException extends SecurityException {

    public RoleNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static RoleNotFoundException notFound() {
        return new RoleNotFoundException(ErrorCode.ROLE_NOT_FOUND);
    }
}
