package com.train.codeai.controller;


import com.train.codeai.advisor.TokenUsageAuditAdvisor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String getResponse(@RequestParam("prompt") String prompt){
        log.info("prompt = {} received",prompt);
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

    }
}
