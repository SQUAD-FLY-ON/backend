package com.choisong.flyon.global.advice;

import com.choisong.flyon.global.exception.BusinessException;
import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ExternalApiException;
import com.choisong.flyon.global.exception.SecurityException;
import com.choisong.flyon.global.exception.ValidationException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(SecurityException.class)
    public ErrorResponse handleExternalApiException(SecurityException e, HttpServletResponse httpServletResponse) {
        loggingError(e.getErrorCode());
        httpServletResponse.setStatus(e.getErrorCode().getHttpStatus().value());
        return new ErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
        HttpServletResponse response) {
        String errorMessage = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse("유효하지 않은 요청입니다.");

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        loggingError(errorCode);
        response.setStatus(errorCode.getHttpStatus().value());
        return new ErrorResponse(
            errorCode.getHttpStatus().value(),
            errorCode.getHttpStatus().getReasonPhrase(),
            errorCode.getCode(),
            errorMessage
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e,
        HttpServletResponse response) {
        String errorMessage = e.getConstraintViolations()
            .stream()
            .findFirst()
            .map(v -> v.getMessage())
            .orElse("유효하지 않은 요청입니다.");

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        loggingError(errorCode);
        response.setStatus(errorCode.getHttpStatus().value());
        return new ErrorResponse(
            errorCode.getHttpStatus().value(),
            errorCode.getHttpStatus().getReasonPhrase(),
            errorCode.getCode(),
            errorMessage
        );
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
