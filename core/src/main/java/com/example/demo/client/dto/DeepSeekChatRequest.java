package com.example.demo.client.dto;

import com.example.demo.controller.dto.request.ChatMessageRequest;
import lombok.Data;

import java.util.List;

//Сообщение пользователя к ии
@Data
public class DeepSeekChatRequest {
    private String model = "deepseek-chat";  // Модель по умолчанию
    private List<ChatMessageRequest> messages;
    private int max_tokens = 500; // Ограничение длины ответа

}
