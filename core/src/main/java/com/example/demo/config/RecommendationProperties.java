package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "recommendations")
public class RecommendationProperties {
    private int maxFetchLimit = 30; // дефолт, если не указан в properties

    public int getMaxFetchLimit() {
        return maxFetchLimit;
    }

    public void setMaxFetchLimit(int maxFetchLimit) {
        this.maxFetchLimit = maxFetchLimit;
    }
}
