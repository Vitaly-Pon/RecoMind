package com.example.demo.deepseek.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

//Сообщение пользователя и настройки к ии
@Data
@Builder
public class DeepSeekChatRequest {
    @Builder.Default
    private String model = "deepseek-chat";  // модель по умолчанию
    private List<ChatMessageRequest> messages;
    private int maxTokens;
}
