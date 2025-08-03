package com.example.demo.controller.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationRequest {
    private String model = "deepseek-chat";  // Модель по умолчанию
    private List<Message> messages;

    @Data
    public static class Message {
        private String role;  // "user", "system", "assistant"
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

}
