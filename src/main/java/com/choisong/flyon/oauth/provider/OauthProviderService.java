package com.choisong.flyon.oauth.provider;

public interface OauthProviderService {

    OauthProviderType getOauthProviderType();

    String getRedirectionLoginUrl();

    OauthMember getOauthMember(String authCode);
}
