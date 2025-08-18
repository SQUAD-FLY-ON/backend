package com.choisong.flyon.gpt.service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptService {

    private final OpenAIClient client;

    public void summarize(String userPrompt) {
        ResponseCreateParams params = ResponseCreateParams.builder()
            .model(ChatModel.O4_MINI)
            .input(userPrompt)
            .build();

        Response res = client.responses().create(params);
        System.out.print(res.toString());
    }
}
