package com.train.codeai.controller;

import com.train.codeai.pojo.ChatRequest;
import com.train.codeai.service.ChatMemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Slf4j
public class ChatMemoryController {

    private final ChatMemoryService chatMemoryService;

    public ChatMemoryController(ChatMemoryService chatMemoryService ){
        this.chatMemoryService = chatMemoryService;
    }

    @PostMapping("/memory")
    public String getResponse(@RequestBody ChatRequest chatRequest){
        return chatMemoryService.getResponse(chatRequest);
    }
}