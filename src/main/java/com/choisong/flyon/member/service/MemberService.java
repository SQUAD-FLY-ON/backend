package com.choisong.flyon.member.service;


import com.choisong.flyon.member.domain.Member;
import com.choisong.flyon.member.exception.MemberNotFoundException;
import com.choisong.flyon.member.repository.MemberRepository;
import com.choisong.flyon.member.service.mapper.MemberMapper;
import com.choisong.flyon.oauth.provider.OauthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public Long updateOrSave(final OauthMember oauthMember) {
        return memberRepository
            .findByOauth2ProviderAndOauth2Id(
                oauthMember.oauthProviderType(), oauthMember.oauthId())
            .map(
                existingMember -> {
                    existingMember.updateNicknameAndImgUrl(
                        oauthMember.nickname(), oauthMember.imgUrl());
                    return existingMember.getId();
                })
            .orElseGet(() -> createMember(oauthMember));
    }

    private Long createMember(final OauthMember oauthMember) {
        final Member newMember = memberMapper.toMember(oauthMember);
        return memberRepository.save(newMember).getId();
    }

    public Member getMember(final Long memberId) {
        return memberRepository
            .findByIdWithRoles(memberId)
            .orElseThrow(MemberNotFoundException::notFound);
    }
}
