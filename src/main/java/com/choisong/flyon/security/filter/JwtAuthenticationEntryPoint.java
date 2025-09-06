package com.choisong.flyon.security.filter;

import com.choisong.flyon.global.advice.GlobalExceptionHandler.ErrorResponse;
import com.choisong.flyon.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ErrorCode e = ErrorCode.ACCESSTOKEN_HEADER_NOT_FOUND;
        final String errorResponse =
            objectMapper.writeValueAsString(new ErrorResponse(e));
        response.setStatus(e.getHttpStatus().value());
        response.getWriter().write(errorResponse);
    }
}
