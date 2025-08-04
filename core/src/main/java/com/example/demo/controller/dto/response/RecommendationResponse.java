package com.example.demo.controller.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationResponse {
    private String id;
    private List<RecommendationChoice> choices;

    @Data
    public static class Choice {
        private Message message;
        private String finishReason;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
