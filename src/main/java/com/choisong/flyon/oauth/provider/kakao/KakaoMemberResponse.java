package com.choisong.flyon.oauth.provider.kakao;

import com.choisong.flyon.oauth.provider.OauthMember;
import com.choisong.flyon.oauth.provider.OauthProviderType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoMemberResponse(Long id, KakaoAccount kakaoAccount) {

    public OauthMember toOauthMember() {
        return OauthMember.builder()
            .oauthId(this.id().toString())
            .nickname(this.kakaoAccount().profile().nickname())
            .imgUrl(this.kakaoAccount().profile().profileImageUrl())
            .oauthProviderType(OauthProviderType.KAKAO)
            .build();
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record KakaoAccount(Profile profile) {

    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Profile(String nickname, String profileImageUrl) {

    }
}
