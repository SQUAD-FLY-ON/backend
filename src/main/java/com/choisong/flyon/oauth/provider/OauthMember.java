package com.choisong.flyon.oauth.provider;

import com.choisong.flyon.oauth.OauthProviderType;
import lombok.Builder;

@Builder
public record OauthMember(
    String oauthId, String imgUrl, String nickname, OauthProviderType oauthProviderType) {

}
