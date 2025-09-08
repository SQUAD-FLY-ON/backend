package com.choisong.flyon.jwt.service;

import com.choisong.flyon.member.domain.Roles;
import com.choisong.flyon.member.repository.RoleRepository;
import com.choisong.flyon.security.exception.CanNotParseTokenException;
import com.choisong.flyon.security.exception.RoleNotFoundException;
import com.choisong.flyon.security.exception.TokenExpiredException;
import com.choisong.flyon.security.principal.MemberPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtValidator {

    private static final String EMPTY_CREDENTIAL = "";
    private final RoleRepository roleRepository;
    private final SecretKey secretKey;

    public Authentication getAuthentication(final String token) {
        final String memberId = validateAccessTokenAndGetClaim(token);
        final List<Roles> roles =
            roleRepository.findByMemberId(Long.parseLong(memberId));
        checkRoleExist(roles);
        final MemberPrincipal memberPrincipal =
            new MemberPrincipal(validateAccessTokenAndGetClaim(token), null, roles);
        return new UsernamePasswordAuthenticationToken(
            memberPrincipal, EMPTY_CREDENTIAL, getAuthorityList(roles));
    }

    private void checkRoleExist(final List<Roles> roles) {
        if (roles.isEmpty()) {
            throw RoleNotFoundException.notFound();
        }
    }

    public String validateAccessTokenAndGetClaim(final String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (final ExpiredJwtException e) {
            throw TokenExpiredException.accessTokenExpired();
        } catch (final JwtException e) {
            throw CanNotParseTokenException.canNotParse();
        }
    }

    public String validateRefreshTokenAndGetClaim(final String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (final ExpiredJwtException e) {
            throw TokenExpiredException.refreshTokenExpired();
        } catch (final JwtException e) {
            throw CanNotParseTokenException.canNotParse();
        }
    }

    private List<GrantedAuthority> getAuthorityList(final List<Roles> roles) {
        return AuthorityUtils.createAuthorityList(
            roles.stream().map(r -> r.getMemberRole().name()).toList());
    }
}
