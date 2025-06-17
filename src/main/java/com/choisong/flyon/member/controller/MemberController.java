package com.choisong.flyon.member.controller;

import com.choisong.flyon.member.dto.MemberRegisterRequest;
import com.choisong.flyon.member.service.MemberService;
import com.choisong.flyon.security.annotation.NoAuthRequired;
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
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @NoAuthRequired
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody MemberRegisterRequest request){
        memberService.createMember(request);
    }
}
