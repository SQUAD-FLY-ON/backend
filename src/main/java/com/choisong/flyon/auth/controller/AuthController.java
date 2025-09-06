package com.choisong.flyon.auth.controller;

import com.choisong.flyon.auth.dto.LoginRequest;
import com.choisong.flyon.auth.dto.LoginResponse;
import com.choisong.flyon.auth.dto.MemberInfo;
import com.choisong.flyon.auth.service.AuthService;
import com.choisong.flyon.jwt.dto.MemberTokens;
import com.choisong.flyon.jwt.service.JwtService;
import com.choisong.flyon.security.annotation.NoAuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Fly:On 자체 로그인")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping
    @Operation(summary = "Fly:On 로그인", description = "ID Password로 로그인합니다.")
    @NoAuthRequired
    public LoginResponse serviceLogin(@RequestBody LoginRequest request) {
        MemberInfo memberInfo = authService.serviceLogin(request);
        MemberTokens memberTokens = jwtService.createAndSaveMemberTokens(memberInfo.memberId());
        return new LoginResponse(memberInfo, memberTokens.accessToken(), memberTokens.refreshToken());
    }
}
