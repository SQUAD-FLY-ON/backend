package com.choisong.flyon.gpt.config;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GptConfig {

    OpenAIClient client = OpenAIOkHttpClient.fromEnv();

    ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
        .addUserMessage("Say this is a test")
        .model(ChatModel.O4_MINI)
        .build();
    ChatCompletion chatCompletion = client.chat().completions().create(params);

}
