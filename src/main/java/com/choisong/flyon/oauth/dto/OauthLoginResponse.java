package com.choisong.flyon.oauth.dto;

import lombok.Builder;

@Builder
public record OauthLoginResponse(OauthMemberResponse oauth2MemberResponse,
                                 String accessToken) {

}
