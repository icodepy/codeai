package com.train.codeai.service;

import com.train.codeai.pojo.ChatRequest;
import com.train.codeai.pojo.OrderDetails;

public interface ChatMemoryService {
    String getResponse(ChatRequest chatRequest);
    String getOrderDetails(ChatRequest chatRequest);
}
