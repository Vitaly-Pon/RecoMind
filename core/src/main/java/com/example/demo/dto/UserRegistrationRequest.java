    package com.example.demo.dto;

    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Size;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class UserRegistrationRequest {
        @NotBlank(message = "Требуется ввести имя пользователя")
        private String username;

        @NotBlank(message = "Требуется ввести пароль")
        @Size(min = 5, message = "Пароль должен содержать более 5 символов")
        private String password;
    }
