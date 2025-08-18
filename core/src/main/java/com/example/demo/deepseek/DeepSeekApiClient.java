package com.example.demo.deepseek;

import com.example.demo.config.DeepSeekConfig;
import com.example.demo.deepseek.dto.ChatMessageRequest;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.deepseek.dto.DeepSeekChatRequest;
import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.exception.DeepSeekApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.List;

@AllArgsConstructor
@Component
public class DeepSeekApiClient {

    private final DeepSeekConfig config;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final MovieResponseParser parser;

    public MovieRecommendationsResponse getRecommendations(String prompt, int count) {
        var request = buildRequest(prompt, count);
        var rawResponse = send(request);
        return parser.parse(rawResponse);
    }

    private DeepSeekChatRequest buildRequest(String prompt, int count) {
        int maxTokens = Math.min(count * 100, config.getMaxAllowedTokens());
        return DeepSeekChatRequest.builder()
                .messages(List.of(new ChatMessageRequest("user", prompt)))
                .maxTokens(maxTokens)
                .responseFormat(new DeepSeekResponseFormat("json_object"))
                .build();
    }

    @SneakyThrows
    private String send(DeepSeekChatRequest request) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(config.getKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        var response = restTemplate.exchange(
                config.getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new DeepSeekApiException("Ошибка запроса: " + response.getStatusCode());
        }

        var dto = mapper.readValue(response.getBody(), DeepSeekChatResponse.class);
        if (dto.getChoices() == null || dto.getChoices().isEmpty()) {
            throw new DeepSeekApiException("Невалидный ответ от API");
        }

        return dto.getChoices().get(0).getMessage().getContent();
    }
}