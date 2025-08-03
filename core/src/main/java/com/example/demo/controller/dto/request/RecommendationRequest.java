package com.example.demo.controller.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationRequest {
    private String model = "deepseek-chat";  // Модель по умолчанию
    private List<Message> messages;
    private int n = 1; // Количество вариантов ответа???
    private int max_tokens = 500; // Ограничение длины ответа

    @Data
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

}
