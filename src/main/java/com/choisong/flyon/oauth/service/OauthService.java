package com.choisong.flyon.oauth.service;

import com.choisong.flyon.member.service.MemberService;
import com.choisong.flyon.oauth.dto.OauthMemberResponse;
import com.choisong.flyon.oauth.provider.OauthMember;
import com.choisong.flyon.oauth.provider.OauthProviderSelector;
import com.choisong.flyon.oauth.provider.OauthProviderService;
import com.choisong.flyon.oauth.provider.OauthProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final OauthProviderSelector oauthProviderSelector;
    private final MemberService memberService;

    public String getRedirectionLoginUrl(final OauthProviderType oauth2ProviderType) {
        final OauthProviderService providerService =
            oauthProviderSelector.getProvider(oauth2ProviderType);
        return providerService.getRedirectionLoginUrl();
    }

    public OauthMemberResponse oauthLogin(
        final OauthProviderType oauth2ProviderType, final String authCode) {
        final OauthProviderService providerService =
            oauthProviderSelector.getProvider(oauth2ProviderType);
        final OauthMember oauth2Member = providerService.getOauthMember(authCode);
        final Long memberId = memberService.updateOrSaveOauthMember(oauth2Member);
        return new OauthMemberResponse(
            memberId.toString(), oauth2Member.nickname(), oauth2Member.imgUrl());
    }
}
