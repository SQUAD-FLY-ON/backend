package com.choisong.flyon.member.controller;


import com.choisong.flyon.member.dto.AdditionalInfoRequest;
import com.choisong.flyon.member.dto.AdditionalInfoResponse;
import com.choisong.flyon.member.dto.PrivateMemberResponse;
import com.choisong.flyon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PatchMapping
    public AdditionalInfoResponse updateAdditionalInfo(
        @RequestBody final AdditionalInfoRequest additionalInfoRequest,
        @AuthMemberId final Long memberId) {
        return memberService.updateAdditionalInfo(additionalInfoRequest, memberId);
    }

    @GetMapping("/private")
    public PrivateMemberResponse getPrivateMember(@AuthMemberId final Long memberId) {
        return memberService.getPrivateMember(memberId);
    }
}
