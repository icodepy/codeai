package com.train.codeai.service.impl;

import com.train.codeai.pojo.ChatRequest;
import com.train.codeai.service.ChatMemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatMemoryServiceImpl implements ChatMemoryService {

    private ChatClient chatClient;
    private ChatMemory chatMemory;

    public ChatMemoryServiceImpl(ChatClient chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.chatMemory =  chatMemory;
    }


    @Override
    public String getResponse(ChatRequest chatRequest) {
        log.info("prompt = {} received",chatRequest.message());
        return chatClient
                .prompt()
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId(chatRequest.messageId()) // this id prevents the overlapping of more than one conversation
                        .build())
                .user(chatRequest.message())
                .call()
                .content();
    }
}
