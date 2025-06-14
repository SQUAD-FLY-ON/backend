package com.choisong.flyon.oauth.provider;

import com.choisong.flyon.oauth.OauthProviderType;

public interface OauthProviderService {

    OauthProviderType getOauthProviderType();

    String getRedirectionLoginUrl();

    OauthMember getOauthMember(String authCode, String redirectUrl);
}
