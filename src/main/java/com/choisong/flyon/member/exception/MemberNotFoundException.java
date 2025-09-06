package com.choisong.flyon.member.exception;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static MemberNotFoundException notFound() {
        return new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
    }
}
