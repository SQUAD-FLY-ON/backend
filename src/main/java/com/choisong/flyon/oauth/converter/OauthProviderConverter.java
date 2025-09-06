package com.choisong.flyon.oauth.converter;

import com.choisong.flyon.oauth.exception.OauthProviderNotFoundException;
import com.choisong.flyon.oauth.provider.OauthProviderType;
import java.util.Locale;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OauthProviderConverter implements Converter<String, OauthProviderType> {

    @Override
    public OauthProviderType convert(final String source) {
        try {
            return OauthProviderType.valueOf(source.toUpperCase(Locale.ROOT));
        } catch (final IllegalArgumentException e) {
            throw OauthProviderNotFoundException.convertFailed();
        }
    }
}
