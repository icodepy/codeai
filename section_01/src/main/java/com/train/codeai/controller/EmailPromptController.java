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
public class EmailPromptController {

    private ChatClient chatClient;

    public EmailPromptController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;

    @Value("classpath:/promptTemplates/systemPropmtTemplate.st")
    Resource systemPromptTemplate;

    @GetMapping("/email")
    public String getResponse(@RequestParam("customerName") String customerName,
                              @RequestParam("customerMessage") String customerMessage){
        log.info("message received by email assistant");
        return chatClient.prompt()
                .system("""
                        You are a professional customer service assistant which helps drafting email
                        responses to improve the productivity of the customer support team
                        """)
                .user(prompTemplateSpec-> prompTemplateSpec.text(userPromptTemplate)
                        .param("customerName", customerName)
                        .param("customerMessage", customerMessage)) // you can use  a map in the params method as well
                .call()
                .content();
    }

}
