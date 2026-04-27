package com.train.codeai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder){
        chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    public String getResponse(@RequestParam("prompt") String prompt){
        return chatClient.prompt(prompt).call().content();
    }
}
