package com.choisong.flyon.oauth.provider.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOauthProperties(
    String loginUri,
    String redirectUri,
    String clientId,
    String clientSecret,
    String[] scope) {

}
