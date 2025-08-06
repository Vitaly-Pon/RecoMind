package com.example.demo.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageRequest {
    private String role;
    private String content;
}
