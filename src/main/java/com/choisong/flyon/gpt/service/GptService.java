package com.choisong.flyon.gpt.service;

import com.openai.client.OpenAIClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptService {

    private final OpenAIClient  openAIClient;



}
