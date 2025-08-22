    package com.example.demo.exception;

    import lombok.AllArgsConstructor;
    import lombok.Data;

    import java.time.LocalDateTime;
    import java.util.Map;

    @Data
    @AllArgsConstructor
    public class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private Map<String, String> details; // для ошибок валидации
    }
