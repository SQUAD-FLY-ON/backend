package com.choisong.flyon.oauth.controller;

import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.swagger.ApiErrorCodeExamples;
import com.choisong.flyon.jwt.dto.MemberTokens;
import com.choisong.flyon.jwt.service.JwtService;
import com.choisong.flyon.oauth.dto.OauthLoginResponse;
import com.choisong.flyon.oauth.dto.OauthMemberResponse;
import com.choisong.flyon.oauth.provider.OauthProviderType;
import com.choisong.flyon.oauth.service.OauthService;
import com.choisong.flyon.security.annotation.NoAuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "KAKAO 소셜 로그인")
public class OauthController {

    private final OauthService oauthService;
    private final JwtService jwtService;

    @GetMapping("/{oauthProviderType}")
    @NoAuthRequired
    @Operation(summary = "카카오 로그인 페이지 요청", description = "카카오 로그인 페이지로 리다이렉션합니다. 사용자가 로그인을 완료하면 code 쿼리파라미터가 포함된 "
        + "리다이렉션 페이지로 리다이렉션됩니다.")
    @ApiErrorCodeExamples({ErrorCode.PROVIDER_NOT_FOUND, ErrorCode.CONVERTING_FAILED})
    public void redirectOauthLoginUrl(
        @PathVariable final OauthProviderType oauthProviderType,
        final HttpServletResponse response)
        throws IOException {
        String redirectionLoginUrl = oauthService.getRedirectionLoginUrl(oauthProviderType);
        response.sendRedirect(redirectionLoginUrl);
    }

    @PostMapping("/{oauthProviderType}")
    @NoAuthRequired
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 페이지 요청에서 받은 code 쿼리파라미터를 authCode 파라미터에 포함시켜야합니다. 액세스토큰은 "
        + "바디에 리프래쉬토큰은 쿠키에 저장됩니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public OauthLoginResponse oauthLogin(
        @PathVariable final OauthProviderType oauthProviderType,
        @RequestParam final String authCode,
        final HttpServletResponse response) {
        final OauthMemberResponse oauthMemberResponse =
            oauthService.oauthLogin(oauthProviderType, authCode);
        final MemberTokens tokens =
            jwtService.createAndSaveMemberTokens(oauthMemberResponse.memberId());
        return new OauthLoginResponse(oauthMemberResponse, tokens.accessToken());
    }

}
