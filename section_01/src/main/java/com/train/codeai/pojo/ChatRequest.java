package com.train.codeai.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record ChatRequest(String messageId, String message){
}
