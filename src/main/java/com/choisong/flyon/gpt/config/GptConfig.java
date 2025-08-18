package com.choisong.flyon.gpt.config;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GptConfig {

    @Value("${open-ai.api.key}")
    private String secretKey;

    @Bean
    public OpenAIClient customizedClient() {
        return OpenAIOkHttpClient.builder()
            .apiKey(secretKey)
            .build();
    }
}
