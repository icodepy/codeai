package com.train.codeai.service.impl;

import com.train.codeai.pojo.ChatRequest;
import com.train.codeai.pojo.OrderDetails;
import com.train.codeai.service.ChatMemoryService;
import com.train.codeai.tool.OrderTool;
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
    private OrderTool orderTool;

    public ChatMemoryServiceImpl(ChatClient chatClient, ChatMemory chatMemory, OrderTool orderTool) {
        this.chatClient = chatClient;
        this.chatMemory =  chatMemory;
        this.orderTool = orderTool;
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

    @Override
    public String getOrderDetails(ChatRequest chatRequest) {


        return      chatClient
                .prompt()
                         .tools(orderTool)
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId(chatRequest.messageId()) // this id prevents the overlapping of more than one conversation
                        .build())
                   .system("""
                           You are a customer support assistant.
                           If the user asks about an order, you MUST use the getOrder tool.
                                   Never guess or invent an order status.
                                   """)
                .user(chatRequest.message())
                .call()
                   .content();

//                .entity(OrderDetails.class);
    }
}
