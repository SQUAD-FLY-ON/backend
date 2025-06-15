package com.choisong.flyon.jwt.service;

import com.choisong.flyon.jwt.domain.RefreshToken;
import com.choisong.flyon.jwt.dto.MemberTokens;
import com.choisong.flyon.jwt.exception.RefreshTokenNotFoundException;
import com.choisong.flyon.jwt.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtCreator jwtCreator;
    private final RefreshTokenRepository refreshTokenRepository;

    public MemberTokens reissue(final String refreshToken) {
        final RefreshToken savedRefreshToken = findByRefreshToken(refreshToken);
        final String savedMemberId = savedRefreshToken.getMemberId();
        refreshTokenRepository.delete(savedRefreshToken);
        return createAndSaveMemberTokens(savedMemberId);
    }

    private RefreshToken findByRefreshToken(final String token) {
        return refreshTokenRepository
            .findById(token)
            .orElseThrow(RefreshTokenNotFoundException::tokenNotFound);
    }

    public MemberTokens createAndSaveMemberTokens(final String memberId) {
        final String accessToken = jwtCreator.create(memberId, jwtProperties.accessExpiration());
        final String refreshToken = jwtCreator.create(memberId, jwtProperties.refreshExpiration());
        refreshTokenRepository.save(new RefreshToken(refreshToken, memberId));
        return new MemberTokens(accessToken, refreshToken);
    }

    public void logout(final Optional<Cookie> cookie) {
        final Cookie tokenCookie = cookie.orElseThrow(
            RefreshTokenNotFoundException::tokenCookieNotFound);
        final String refreshToken = tokenCookie.getValue();
        refreshTokenRepository.deleteById(refreshToken);
    }
}
