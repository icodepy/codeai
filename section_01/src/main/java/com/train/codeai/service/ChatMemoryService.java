package com.train.codeai.service;

import com.train.codeai.pojo.ChatRequest;

public interface ChatMemoryService {
    String getResponse(ChatRequest chatRequest);
}
