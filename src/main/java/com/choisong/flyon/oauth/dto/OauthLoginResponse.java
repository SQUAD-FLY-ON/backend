package com.choisong.flyon.oauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record OauthLoginResponse(OauthMemberResponse oauth2MemberResponse,
                                 String accessToken) {

}
