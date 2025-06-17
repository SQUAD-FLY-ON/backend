package com.choisong.flyon.member.service;


import com.choisong.flyon.jwt.service.JwtService;
import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.domain.MemberRole;
import com.choisong.flyon.member.domain.Roles;
import com.choisong.flyon.member.dto.MemberRegisterRequest;
import com.choisong.flyon.member.exception.LoginIdDuplicatedException;
import com.choisong.flyon.member.exception.MemberNotFoundException;
import com.choisong.flyon.member.exception.NicknameDuplicatedException;
import com.choisong.flyon.member.repository.MemberRepository;
import com.choisong.flyon.member.mapper.MemberMapper;
import com.choisong.flyon.member.repository.RoleRepository;
import com.choisong.flyon.oauth.provider.OauthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public Long updateOrSaveOauthMember(final OauthMember oauthMember) {
        return memberRepository
            .findByOauth2ProviderAndOauth2Id(
                oauthMember.oauthProviderType(), oauthMember.oauthId())
            .map(
                existingMember -> {
                    existingMember.updateNicknameAndImgUrl(
                        oauthMember.nickname(), oauthMember.imgUrl());
                    return existingMember.getId();
                })
            .orElseGet(() -> createOauthMember(oauthMember));
    }

    @Transactional
    public void createMember(final MemberRegisterRequest request) {
        validateLoginId(request.loginId());
        validateNickName(request.nickname());
        String encodePassword = encodePassword(request.password());
        Member member = memberMapper.from(request,encodePassword);
        memberRepository.save(member);
        roleRepository.save(new Roles(member.getId(), MemberRole.ROLE_MEMBER));
    }

    private String encodePassword(final String password) {
        return passwordEncoder.encode(password);
    }

    private void validateNickName(final String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw NicknameDuplicatedException.duplicated();
        }
    }

    private void validateLoginId(final String loginId) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw LoginIdDuplicatedException.duplicated();
        }
    }

    private Long createOauthMember(final OauthMember oauthMember) {
        final Member newMember = memberMapper.from(oauthMember);
        return memberRepository.save(newMember).getId();
    }

    public Member getMemberByLoginId(final String loginId){
        return memberRepository.findByLoginId(loginId)
            .orElseThrow(MemberNotFoundException::notFound);
    }
}
