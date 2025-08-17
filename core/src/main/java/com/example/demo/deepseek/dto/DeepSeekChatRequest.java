package com.example.demo.deepseek.dto;

import com.example.demo.deepseek.DeepSeekResponseFormat;
import lombok.Data;

import java.util.List;

//Сообщение пользователя и настройки к ии
@Data
public class DeepSeekChatRequest {
    private String model = "deepseek-chat";  // Модель по умолчанию
    private List<ChatMessageRequest> messages;
    private int maxTokens = 500; // Ограничение длины ответа
    private DeepSeekResponseFormat responseFormat; // формат для api (JSON)

}
