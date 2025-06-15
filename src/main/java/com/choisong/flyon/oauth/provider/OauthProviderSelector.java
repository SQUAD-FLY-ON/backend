package com.choisong.flyon.oauth.provider;

import com.choisong.flyon.oauth.exception.OauthProviderNotFoundException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OauthProviderSelector {

    private final Map<OauthProviderType, OauthProviderService> providerServices;

    public OauthProviderSelector(final Set<OauthProviderService> providerServices) {
        this.providerServices =
            providerServices.stream()
                .collect(
                    Collectors.toMap(
                        OauthProviderService::getOauthProviderType,
                        Function.identity()));
    }

    public OauthProviderService getProvider(final OauthProviderType oauth2ProviderType) {
        return Optional.ofNullable(providerServices.get(oauth2ProviderType))
            .orElseThrow(OauthProviderNotFoundException::providerNotFound);
    }
}
