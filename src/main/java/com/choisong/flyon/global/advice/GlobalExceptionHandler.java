package com.choisong.flyon.global.advice;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ExternalApiException;
import com.choisong.flyon.global.exception.ValidationException;
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

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleBusinessException(BusinessException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleValidationException(ValidationException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(ExternalApiException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleExternalApiException(ExternalApiException e) {
        loggingError(e.getErrorCode());
        return new ErrorResponse(e.getErrorCode());
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
