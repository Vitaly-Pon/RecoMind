package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "deepseek.api")
public class DeepSeekConfig {
    private String key;
    private String url;
    private int maxAllowedTokens = 1200; // добавлено private
}
