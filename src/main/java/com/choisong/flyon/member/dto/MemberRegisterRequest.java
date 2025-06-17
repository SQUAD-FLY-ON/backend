package com.choisong.flyon.member.dto;

import com.choisong.flyon.oauth.provider.OauthProviderType;

public record MemberRegisterRequest(
    String nickname,
    String loginId,
    String password,
    OauthProviderType oauthProviderType
) {

}
