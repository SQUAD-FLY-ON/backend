package com.choisong.flyon.oauth.config;

import com.choisong.flyon.oauth.provider.kakao.KakaoOauthProperties;
import com.choisong.flyon.oauth.provider.kakao.KakaoRedirectionLoginUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@RequiredArgsConstructor
public class RedirectLonginUrlBeanFactory {

    private final KakaoOauthProperties kakaoOauth2Properties;

    @Bean
    public KakaoRedirectionLoginUrl redirectionLoginUrl() {
        return new KakaoRedirectionLoginUrl(
            UriComponentsBuilder.fromUriString(kakaoOauth2Properties.loginUri())
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoOauth2Properties.clientId())
                .queryParam("redirect_uri", kakaoOauth2Properties.redirectUri())
                .queryParam("scope", String.join(",", kakaoOauth2Properties.scope()))
                .toUriString());
    }
}
