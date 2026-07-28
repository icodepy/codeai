package com.train.codeai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Slf4j
public class SystemPropmtTemplate {

    private ChatClient chatClient;

    public SystemPropmtTemplate(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    @Value("classpath:/promptTemplates/systemPropmtTemplate.st")
    Resource systemPromptTemplate;


    @GetMapping("/prompt-stuffing") //  for eample provnding policies to the llm, to respond to the queries
    public String getResponsePromptStuffing(@RequestParam("message") String message){
        log.info("message received by the assistant");
        return chatClient.prompt()
                .system(systemPromptTemplate)
                .user(message)
                .call()
                .content();
    }
}
