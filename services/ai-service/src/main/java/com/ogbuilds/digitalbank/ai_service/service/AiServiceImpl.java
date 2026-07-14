package com.ogbuilds.digitalbank.ai_service.service;

import com.ogbuilds.digitalbank.ai_service.config.SystemConfig;
import com.ogbuilds.digitalbank.ai_service.dto.ChatRequest;
import com.ogbuilds.digitalbank.ai_service.dto.ChatResponse;
import com.ogbuilds.digitalbank.ai_service.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService{

    private final ChatClient chatClient;


    @Override
    public ChatResponse chat(ChatRequest request, Long authUserId) {

        UserContext.setUserId(authUserId);

        try {

            String response = chatClient
                    .prompt()
                    .system(SystemConfig.systemPrompt())
                    .user(request.getMessage())
                    .call()
                    .content();

            return ChatResponse.builder()
                    .response(response)
                    .build();

        } finally {

            UserContext.clear();

        }
    }
}
