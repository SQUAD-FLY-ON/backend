package com.choisong.flyon.global.advice;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ExternalApiException;
import com.choisong.flyon.global.exception.ValidationException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ErrorResponse handleBusinessException(BusinessException e, HttpServletResponse httpServletResponse) {
        loggingError(e.getErrorCode());
        httpServletResponse.setStatus(e.getErrorCode().getHttpStatus().value());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleValidationException(ValidationException e, HttpServletResponse httpServletResponse) {
        loggingError(e.getErrorCode());
        httpServletResponse.setStatus(e.getErrorCode().getHttpStatus().value());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(ExternalApiException.class)
    public ErrorResponse handleExternalApiException(ExternalApiException e, HttpServletResponse httpServletResponse) {
        loggingError(e.getErrorCode());
        httpServletResponse.setStatus(e.getErrorCode().getHttpStatus().value());
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
