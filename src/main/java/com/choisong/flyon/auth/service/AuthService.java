package com.choisong.flyon.auth.service;

import com.choisong.flyon.auth.dto.LoginRequest;
import com.choisong.flyon.auth.dto.MemberInfo;
import com.choisong.flyon.auth.exception.PasswordNotMatchedException;
import com.choisong.flyon.auth.mapper.AuthMapper;
import com.choisong.flyon.jwt.service.JwtService;
import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final JwtService jwtService;

    public MemberInfo serviceLogin(final LoginRequest request) {
        Member member = memberService.getMemberByLoginId(request.loginId());
        validatePassword(member, request.password());
        return authMapper.from(member);
    }

    private void validatePassword(final Member member, final String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, member.getEncodedPassword())) {
            throw PasswordNotMatchedException.notMatched();
        }
    }
}
