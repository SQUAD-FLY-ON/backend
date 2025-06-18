package com.choisong.flyon.member.controller;

import com.choisong.flyon.member.dto.MemberRegisterRequest;
import com.choisong.flyon.member.service.MemberService;
import com.choisong.flyon.security.annotation.NoAuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
@Tag(name = "회원")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @NoAuthRequired
    @Operation(summary =
        "Fly:On 자체 회원 가입 [소셜로그인X]", description = "소셜로그인이 아닌 ID Password 방식의 로그인입니다. 후에 Deprecated 될 수 있습니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody MemberRegisterRequest request) {
        memberService.createMember(request);
    }
}
