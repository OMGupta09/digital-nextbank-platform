package com.ogbuilds.digitalbank.ai_service.service;

import com.ogbuilds.digitalbank.ai_service.dto.ChatRequest;
import com.ogbuilds.digitalbank.ai_service.dto.ChatResponse;

public interface AiService {

    ChatResponse chat(ChatRequest request, Long authUserId);

}