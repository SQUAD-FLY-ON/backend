package com.choisong.flyon.member.service;


import com.choisong.flyon.member.dto.AdditionalInfoRequest;
import com.choisong.flyon.member.dto.AdditionalInfoResponse;
import com.choisong.flyon.member.dto.PrivateMemberResponse;
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
                oauthMember.oauthProviderType(), oauthMember.oauth2Id())
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

    @Transactional
    public AdditionalInfoResponse updateAdditionalInfo(final AdditionalInfoRequest request, final Long memberId) {
        final Member member = getMember(memberId);
        final String memberCellToken =
            createMemberCellToken(request.latitude(), request.longitude());
        member.validateAndUpdateAdditionalInfo(
            request.githubUrl(),
            request.phoneNumber(),
            request.smsNotificationSetting(),
            memberCellToken);
        return new AdditionalInfoResponse(memberId);
    }

    public Member getMember(final Long memberId) {
        return memberRepository
            .findByIdWithRoles(memberId)
            .orElseThrow(ResourceNotFoundException::memberNotFound);
    }

    private String createMemberCellToken(final double latitude, final double longitude) {
        final S2Point point = S2LatLng.fromDegrees(latitude, longitude).toPoint();
        return S2CellId.fromPoint(point).parent(GoogleS2Const.S2_CELL_LEVEL.getValue()).toToken();
    }

    public PrivateMemberResponse getPrivateMember(final Long memberId) {
        final Member member = getMember(memberId);
        return memberMapper.toPrivateMemberResponse(member);
    }
}
