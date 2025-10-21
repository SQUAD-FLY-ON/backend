package com.choisong.flyon.member.controller;

import com.choisong.flyon.jwt.domain.RefreshToken;
import com.choisong.flyon.jwt.repository.RefreshTokenRepository;
import com.choisong.flyon.member.dto.MemberInfoResponse;
import com.choisong.flyon.member.dto.MemberRegisterRequest;
import com.choisong.flyon.member.dto.MemberUpdateRequest;
import com.choisong.flyon.member.service.MemberService;
import com.choisong.flyon.security.annotation.AuthenticationMember;
import com.choisong.flyon.security.annotation.NoAuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        "Fly:On 자체 회원 가입 [소셜로그인X]", description = "소셜로그인이 아닌 ID Password 방식의 로그인입니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody MemberRegisterRequest request) {
        memberService.createMember(request);
    }

    @GetMapping
    @Operation(summary =
        "회원 정보 조회", description = "회원 정보를 조회합니다. 닉네임, 뱃지, 뱃지 고도, 비행한 고도")
    public MemberInfoResponse findMember(@AuthenticationMember Long memberId) {
        return memberService.findMemberInfo(memberId);
    }

    @PutMapping
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    public void updateMember(@AuthenticationMember Long memberId, @Valid @RequestBody MemberUpdateRequest request) {
        memberService.updateMember(memberId,request);
    }

    @DeleteMapping
    @Operation(summary = "회원 탈퇴", description = "회원 정보를 서버에서 즉시 삭제합니다.")
    public void deleteMember(@AuthenticationMember Long memberId) {
        memberService.deleteMember(memberId);
    }
}
