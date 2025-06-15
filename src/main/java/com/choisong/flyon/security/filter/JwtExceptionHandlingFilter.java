package com.choisong.flyon.security.filter;

import com.choisong.flyon.global.advice.GlobalExceptionHandler.ErrorResponse;
import com.choisong.flyon.global.exception.SecurityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtExceptionHandlingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain)
        throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (final SecurityException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            final String errorResponse =
                objectMapper.writeValueAsString(new ErrorResponse(e.getErrorCode()));
            response.setStatus(e.getErrorCode().getHttpStatus().value());
            response.getWriter().write(errorResponse);
        }
    }
}
