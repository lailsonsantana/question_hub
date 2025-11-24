package com.example.questifysharedapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class OpenAiService {

    private final ContextService contextService;
    private final OpenAiChatModel chatModel;

    public OpenAiService(ContextService cs , OpenAiChatModel openAiChatModel){
        this.contextService = cs;
        this.chatModel = openAiChatModel;
    }

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    @Value("${spring.ai.openai.chat.options.max-tokens}")
    private Integer maxTokens;

    @Value("${spring.ai.openai.chat.options.temperature}")
    private Double temperature;


    public String getClassification(String statement) {
        String context = contextService.getContext(1L).getText();
        ChatResponse response = chatModel.call(
                new Prompt(
                        context + " " + statement,
                        OpenAiChatOptions.builder()
                                .withModel(model)
                                .withTemperature(temperature)
                                .withMaxTokens(maxTokens)
                                .build()
                ));
        log.info("Response {}" , response);
        List<Generation> generations = response.getResults();
        Generation generation = generations.get(0);
        AssistantMessage assistantMessage = generation.getOutput();

        return assistantMessage.getContent();
    }
}

