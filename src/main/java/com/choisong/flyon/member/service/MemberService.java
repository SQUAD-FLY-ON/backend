package com.choisong.flyon.member.service;


import com.choisong.flyon.global.exception.ErrorCode;
import com.choisong.flyon.global.exception.ValidationException;
import com.choisong.flyon.jwt.repository.RefreshTokenRepository;
import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.domain.MemberRole;
import com.choisong.flyon.member.domain.Roles;
import com.choisong.flyon.member.dto.MemberInfoResponse;
import com.choisong.flyon.member.dto.MemberRegisterRequest;
import com.choisong.flyon.member.dto.MemberUpdateRequest;
import com.choisong.flyon.member.exception.LoginIdDuplicatedException;
import com.choisong.flyon.member.exception.MemberNotFoundException;
import com.choisong.flyon.member.exception.NicknameDuplicatedException;
import com.choisong.flyon.member.mapper.MemberMapper;
import com.choisong.flyon.member.repository.MemberRepository;
import com.choisong.flyon.member.repository.RoleRepository;
import com.choisong.flyon.oauth.provider.OauthMember;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final List<String> profileUrls =  List.of(
        "https://github.com/user-attachments/assets/9d326d15-b2ee-4559-9394-05ce4b4b6089",
        "https://github.com/user-attachments/assets/094a9931-b8f1-4317-963d-ba32367bfd95",
        "https://github.com/user-attachments/assets/4b2f2021-6951-455b-9bc5-243e2578b969"
    );

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
        String randomProfileImg = getRandomProfileImg();
        Member member = memberMapper.toEntity(request, encodePassword,randomProfileImg);
        memberRepository.save(member);
        roleRepository.save(new Roles(member.getId(), MemberRole.ROLE_MEMBER));
    }

    private String getRandomProfileImg() {
        Random random = new Random();
        return profileUrls.get(random.nextInt(profileUrls.size()));
    }

    public MemberInfoResponse findMemberInfo(final Long memberId) {
        Member member = getMemberById(memberId);
        return memberMapper.toMemberInfoResponse(member);
    }

    @Transactional
    public void updateMember(final Long memberId, final MemberUpdateRequest request) {
        final Member member = getMemberById(memberId);
        String encodePassword = encodePassword(request.password());
        if(!member.getLoginId().equals(request.loginId())) {
            validateLoginId(request.loginId());
        }
        member.update(request.loginId(), encodePassword, request.nickname());
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
        final Member newMember = memberMapper.toEntity(oauthMember);
        return memberRepository.save(newMember).getId();
    }

    public Member getMemberByLoginId(final String loginId) {
        return memberRepository.findByLoginId(loginId)
            .orElseThrow(MemberNotFoundException::notFound);
    }

    public Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::notFound);
    }

    @Transactional
    public void deleteMember(final Long memberId) {
        memberRepository.deleteById(memberId);
        refreshTokenRepository.deleteByMemberId(String.valueOf(memberId));
    }
}
