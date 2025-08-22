package com.example.demo.deepseek.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

//Сообщение пользователя и настройки к ии
@Data
@Builder
public class DeepSeekChatRequest {
    private String model;
    private List<ChatMessageRequest> messages;
    private int maxTokens;
}
