package com.example.demo.controller.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class DeepSeekChatRequest {
    private String model = "deepseek-chat";  // Модель по умолчанию
    private List<ChatMessageRequest> messages;
    private int max_tokens = 500; // Ограничение длины ответа

}
