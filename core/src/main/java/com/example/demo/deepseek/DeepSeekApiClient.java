package com.example.demo.deepseek;

import com.example.demo.config.DeepSeekConfig;
import com.example.demo.deepseek.dto.ChatMessageRequest;
import com.example.demo.deepseek.dto.DeepSeekChatRequest;
import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.entity.MovieRecommendationModel;
import com.example.demo.exception.DeepSeekApiException;
import com.example.demo.mapper.MovieMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.List;

@AllArgsConstructor
@Component
public class DeepSeekApiClient {

    private final DeepSeekConfig config;
    private final RestTemplate restTemplate;
    private final MovieResponseParser parser;

    public List<MovieRecommendationModel> getRecommendations(String prompt, int count) {
        DeepSeekChatRequest request = buildRequest(prompt, count);
        String rawResponse = send(request);
        List<MovieInfo> infos = parser.parse(rawResponse);
        List<MovieRecommendationModel> domainMovies = MovieMapper.toDomainList(infos);
        return domainMovies;

    }

    private DeepSeekChatRequest buildRequest(String prompt, int count) {
        int maxTokens = Math.min(count * 100, config.getMaxTokens());

        return DeepSeekChatRequest.builder()
                .model(config.getModel())
                .messages(List.of(new ChatMessageRequest("user", prompt)))
                .maxTokens(maxTokens)
                .build();
    }

    @SneakyThrows
    private String send(DeepSeekChatRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(config.getKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<DeepSeekChatResponse> responseEntity = restTemplate.exchange(
                config.getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                DeepSeekChatResponse.class
        );

        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            throw new DeepSeekApiException("Ошибка запроса: " + responseEntity.getStatusCode());
        }

        DeepSeekChatResponse deepSeekChatResponse = responseEntity.getBody();

        if (deepSeekChatResponse.getChoices() == null || deepSeekChatResponse.getChoices().isEmpty()) {
            throw new DeepSeekApiException("Невалидный ответ от API");
        }

        return deepSeekChatResponse.getChoices().get(0).getMessage().getContent();
    }
}