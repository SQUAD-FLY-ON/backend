package com.choisong.flyon.global.advice;

import com.choisong.flyon.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        loggingError(ErrorCode.AUTHORITY_NOT_VALID);
        return new ErrorResponse(ErrorCode.AUTHORITY_NOT_VALID);
    }

    private void loggingError(final ErrorCode errorCode) {
        log.error("ErrorCode : {} , Message : {}", errorCode.getCode(), errorCode.getMsg());
    }

    public record ErrorResponse(
        int httpStatusCode,
        String httpStatusMessage,
        String serverErrorCode,
        String serverErrorMessage) {

        public ErrorResponse(final ErrorCode errorCode) {
            this(
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().getReasonPhrase(),
                errorCode.getCode(),
                errorCode.getMsg());
        }

    }
}
