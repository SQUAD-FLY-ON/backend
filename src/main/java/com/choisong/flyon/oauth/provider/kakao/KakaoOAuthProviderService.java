package com.choisong.flyon.oauth.provider.kakao;

import com.choisong.flyon.oauth.OauthProviderType;
import com.choisong.flyon.oauth.provider.OauthMember;
import com.choisong.flyon.oauth.provider.OauthProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoOAuthProviderService implements OauthProviderService {

    private static final String GRANT_TYPE = "grant_type";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String CODE = "code";
    private static final String CLIENT_ID = "client_id";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String BEARER = "Bearer ";

    private final KakaoRestClient kakaoRestClient;
    private final KakaoOauth2Properties kakaoOauth2Properties;
    private final KakaoRedirectionLoginUrl kakaoRedirectionLoginUrl;

    @Override
    public OauthProviderType getOauth2ProviderType() {
        return OauthProviderType.KAKAO;
    }

    @Override
    public String getRedirectionLoginUrl() {
        return kakaoRedirectionLoginUrl.redirectionUrl();
    }

    @Override
    public OauthMember getOauthMember(final String authCode, final String redirectUrl) {
        final KakaoAuthorization kakaoAuthorization =
            kakaoRestClient.getKakaoAccessToken(requestParams(authCode, redirectUrl));
        final String accessToken = kakaoAuthorization.accessToken();
        log.info("access {}", accessToken);
        final KakaoMemberResponse kakaoMember =
            kakaoRestClient.getKakaoMember(BEARER + accessToken);
        return kakaoMember.toOauth2Member();
    }

    private MultiValueMap<String, String> requestParams(final String authCode,
        final String redirectUrl) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(GRANT_TYPE, AUTHORIZATION_CODE);
        params.add(CLIENT_ID, kakaoOauth2Properties.clientId());
        params.add(REDIRECT_URI, redirectUrl);
        params.add(CODE, authCode);
        params.add(CLIENT_ID, kakaoOauth2Properties.clientSecret());
        return params;
    }
}
