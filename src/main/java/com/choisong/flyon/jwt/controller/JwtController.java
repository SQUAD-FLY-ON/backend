package com.choisong.flyon.jwt.controller;

import com.choisong.flyon.jwt.dto.MemberTokens;
import com.choisong.flyon.jwt.dto.TokenRequest;
import com.choisong.flyon.jwt.dto.TokenResponse;
import com.choisong.flyon.jwt.service.JwtService;
import com.choisong.flyon.security.annotation.NoAuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
@Tag(name = "JWT 토큰 [KAKAO 로그인, Fly:On 자체 로그인 공용]")
public class JwtController {

    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "액세스 토큰 재발급", description = "리프레쉬 토큰을 사용해 액세스토큰을 재발급합니다.")
    @NoAuthRequired
    public TokenResponse reissueAccessToken(@RequestBody TokenRequest request) {
        return new TokenResponse(jwtService.reissue(request.refreshToken()));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "로그아웃", description = "로그아웃합니다.")
    public void logout(@RequestBody TokenRequest request) {
        jwtService.logout(request.refreshToken());
    }
}
