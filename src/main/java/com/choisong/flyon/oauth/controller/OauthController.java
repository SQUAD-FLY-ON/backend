package com.choisong.flyon.oauth.controller;

import com.choisong.flyon.security.jwt.controller.JwtCookieLoader;
import com.choisong.flyon.security.jwt.dto.MemberTokens;
import com.choisong.flyon.security.jwt.service.JwtService;
import com.choisong.flyon.oauth.provider.OauthProviderType;
import com.choisong.flyon.oauth.dto.OauthLoginResponse;
import com.choisong.flyon.oauth.dto.OauthMemberResponse;
import com.choisong.flyon.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

    private final OauthService oauthService;
    private final JwtService jwtService;
    private final JwtCookieLoader jwtCookieLoader;

    @GetMapping("/{oauthProviderType}")
    public void redirectOauthLoginUrl(
        @PathVariable final OauthProviderType oauthProviderType,
        final HttpServletResponse response)
        throws IOException {
        String redirectionLoginUrl = oauthService.getRedirectionLoginUrl(oauthProviderType);
        response.sendRedirect(redirectionLoginUrl);
    }

    @PostMapping("/{oauthProviderType}")
    @ResponseStatus(HttpStatus.CREATED)
    public OauthLoginResponse socialLogin(
        @PathVariable final OauthProviderType oauthProviderType,
        @RequestParam final String authCode,
        final HttpServletResponse response) {
        final OauthMemberResponse oauthMemberResponse =
            oauthService.login(oauthProviderType, authCode);
        final MemberTokens tokens =
            jwtService.createAndSaveMemberTokens(oauthMemberResponse.memberId());
        jwtCookieLoader.loadCookie(response, tokens.refreshToken());
        return new OauthLoginResponse(oauthMemberResponse, tokens.accessToken());
    }

}
