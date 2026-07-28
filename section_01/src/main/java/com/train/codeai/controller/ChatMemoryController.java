package com.train.codeai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Slf4j
public class ChatMemoryController {

    private final ChatClient chatClient;

    public ChatMemoryController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/memory")
    public String getResponse(@RequestParam("prompt") String prompt){
        log.info("prompt = {} received",prompt);
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

    }
}