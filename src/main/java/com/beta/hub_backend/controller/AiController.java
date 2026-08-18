package com.beta.hub_backend.controller;

import com.beta.hub_backend.ai.BetaTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final ChatClient.Builder chatClientBuilder;
    private final BetaTools betaTools;

    private static final String SYSTEM_PROMPT = """
        Hi! 👋 I'm Ask BETA.
I can help you explore BETA Hub — events, teams, alumni, notices, and templates. What would you like to know?
        """;

    public record AskRequest(String question) {}
    public record AskResponse(String answer) {}

    @PostMapping("/ask")
    public ResponseEntity<AskResponse> ask(@RequestBody AskRequest request) {
        String answer = chatClientBuilder.build()
                .prompt()
                .system(SYSTEM_PROMPT)
                .user(request.question())
                .tools(betaTools)
                .call()
                .content();

        return ResponseEntity.ok(new AskResponse(answer));
    }
}