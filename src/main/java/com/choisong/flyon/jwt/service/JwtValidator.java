package com.choisong.flyon.jwt.service;

import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    private static final String EMPTY_CREDENTIAL = "";
    private final MemberRepository memberRepository;
    private final SecretKey secretKey;

    public Authentication getAuthentication(final String token) {
        final String memberId = validateAndGetClaim(token);
        final Member member =
            memberRepository
                .findByIdWithRoles(Long.parseLong(memberId))
                .orElseThrow(JwtAuthenticationException::memberNotFound);
        final MemberPrincipal memberPrincipal =
            new MemberPrincipal(validateAndGetClaim(token), null, member.getRoleNames());
        return new UsernamePasswordAuthenticationToken(
            memberPrincipal, EMPTY_CREDENTIAL, getAuthorityList(member));
    }

    public String validateAndGetClaim(final String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (final ExpiredJwtException e) {
            throw JwtAuthenticationException.accessTokenExpired();
        } catch (final JwtException e) {
            throw JwtAuthenticationException.canNotParseToken();
        }
    }

    private List<GrantedAuthority> getAuthorityList(final Member m) {
        return AuthorityUtils.createAuthorityList(
            m.getRoleNames().stream().map(r -> r.getRole().toString()).toList());
    }

    public boolean areBothNotEqual(
        final String refreshTokenMemberId, final String accessTokenMemberId) {
        return !accessTokenMemberId.equals(refreshTokenMemberId);
    }
}
