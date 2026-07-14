package com.ogbuilds.digitalbank.ai_service.controller;

import com.ogbuilds.digitalbank.ai_service.dto.ChatRequest;
import com.ogbuilds.digitalbank.ai_service.dto.ChatResponse;
import com.ogbuilds.digitalbank.ai_service.service.AiService;
import com.ogbuilds.digitalbank.ai_service.tools.AccountTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/chat")
    public ChatResponse chat(
            @RequestBody ChatRequest request,
            @RequestHeader("X-User-Id") Long authUserId) {

        log.info("Auth User Id = {}", authUserId);

        return aiService.chat(request, authUserId);
    }


}
