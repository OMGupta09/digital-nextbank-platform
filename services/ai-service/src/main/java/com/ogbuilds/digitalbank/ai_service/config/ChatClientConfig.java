package com.ogbuilds.digitalbank.ai_service.config;

import com.ogbuilds.digitalbank.ai_service.tools.AccountTools;
import com.ogbuilds.digitalbank.ai_service.tools.CustomerTools;
import com.ogbuilds.digitalbank.ai_service.tools.TransactionTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(
            OpenAiChatModel chatModel,
            AccountTools accountTools,
            TransactionTools transactionTools,
            CustomerTools customerTools) {

        return ChatClient.builder(chatModel)
                .defaultTools(accountTools, transactionTools, customerTools)
                .build();
    }

}