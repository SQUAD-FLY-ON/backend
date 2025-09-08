package com.choisong.flyon.jwt.service;

import com.choisong.flyon.jwt.domain.RefreshToken;
import com.choisong.flyon.jwt.dto.MemberTokens;
import com.choisong.flyon.jwt.repository.RefreshTokenRepository;
import com.choisong.flyon.security.exception.TokenNotFoundException;
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
    private final JwtValidator jwtValidator;

    public String reissue(final String refreshToken) {
        final RefreshToken savedRefreshToken = findByRefreshToken(refreshToken);
        final String savedMemberId = savedRefreshToken.getMemberId();
        jwtValidator.validateRefreshTokenAndGetClaim(refreshToken);
        return jwtCreator.create(savedMemberId, jwtProperties.accessExpiration());
    }

    private RefreshToken findByRefreshToken(final String token) {
        return refreshTokenRepository
            .findById(token)
            .orElseThrow(TokenNotFoundException::refreshTokenNotFound);
    }

    public MemberTokens createAndSaveMemberTokens(final String memberId) {
        final String accessToken = jwtCreator.create(memberId, jwtProperties.accessExpiration());
        final String refreshToken = jwtCreator.create(memberId, jwtProperties.refreshExpiration());
        refreshTokenRepository.save(new RefreshToken(refreshToken, memberId));
        return new MemberTokens(accessToken, refreshToken);
    }

    public void logout(final String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }
}
