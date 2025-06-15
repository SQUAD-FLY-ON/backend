package com.choisong.flyon.security.jwt.service;

import com.choisong.flyon.security.exception.TokenNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtExtractor {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_PREFIX_LENGTH = TOKEN_PREFIX.length();

    public String extractAccessToken(final String accessTokenWithBearer) {
        if (!StringUtils.hasText(accessTokenWithBearer)
            || !accessTokenWithBearer.startsWith(TOKEN_PREFIX)) {
            throw TokenNotFoundException.accessTokenHeaderNotFound();
        }
        return accessTokenWithBearer.substring(TOKEN_PREFIX_LENGTH);
    }
}
