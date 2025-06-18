package com.choisong.flyon.oauth.dto;

import lombok.Builder;

@Builder
public record OauthMemberResponse(
    String memberId,
    String nickname,
    String imgUrl) {

}
