package com.train.codeai.config;

import com.train.codeai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatMemory getChatMemory(){ // step-1
        // step-2 you've to pass it to the MessageChatMemoryAdvisor in the advisors method
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(30)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        // mini model consume less amount of credits
        ChatOptions chatOptions =  OllamaChatOptions.builder()
                //.model("llama3.2")
                .temperature(0.8)
                .topP(1.0)
                .numPredict(1000)
                .build();  // chatOption
        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsageAuditAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()))
//                .defaultSystem("""
//                        you're an HR assitant of an org. only respond to the queries related to HR business.
//                        For any query outside this domain, Jsut ask the user to connect with the HR
//                        """)
                .defaultUser("Who are you?")
                .build();
    }



}
