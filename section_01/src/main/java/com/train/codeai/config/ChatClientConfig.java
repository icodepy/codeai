package com.train.codeai.config;

import com.train.codeai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        // mini model consume less amount of credits
        ChatOptions chatOptions = OpenAiChatOptions.builder().model("ai/gemma3").temperature(0.8)
                .maxCompletionTokens(1000)
                .frequencyPenalty(1.0)
                .topP(1.0)
                .build();  // chatOptions
        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                        you're an HR assitant of an org. only respond to the queries related to HR business.
                        For any query outside this domain, Jsut ask the user to connect with the HR
                        """)
                .defaultUser("Who are you?")
                .build();
    }
}
