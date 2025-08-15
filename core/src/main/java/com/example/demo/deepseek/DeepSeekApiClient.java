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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Component
public class DeepSeekApiClient {
    private final DeepSeekConfig config;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final MovieResponseParser parser;

    @SneakyThrows
    public MovieRecommendationsResponse getRecommendations(String prompt, int count) {
        DeepSeekChatRequest request = buildRequest(prompt, count);
        String content = sendAPIRequest(request);

        MovieApiResponse movieApiResponse = parser.parse(content);
        List<String> numberedRecommendations = IntStream.range(0, movieApiResponse.getMovies().size()) // Проходим по индексам
                .mapToObj(i -> (i + 1) + ". " + parser.formatMovieInfo(movieApiResponse.getMovies().get(i))) // Добавляем номер перед описанием
                .collect(Collectors.toList());

        return new MovieRecommendationsResponse(numberedRecommendations);
    }
    private DeepSeekChatRequest buildRequest(String prompt, int count) {
        int tokensMovie = 100;
        int requestedTokens = count * tokensMovie;
        int finalTokens = Math.min(requestedTokens, config.getMaxAllowedTokens());

        DeepSeekChatRequest request = new DeepSeekChatRequest();
        request.setMessages(List.of(new ChatMessageRequest("user", prompt)));
        request.setMaxTokens(finalTokens);
        request.setResponseFormat(new DeepSeekResponseFormat("json_object"));
        return request;
    }

    @SneakyThrows
    private String sendAPIRequest(DeepSeekChatRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(config.getKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> rawResponse = restTemplate.exchange(
                config.getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                String.class
        );

        if (rawResponse.getStatusCode() != HttpStatus.OK || rawResponse.getBody() == null) {
            throw new DeepSeekApiException("Ошибка запроса к DeepSeek API: " + rawResponse.getStatusCode());
        }

        DeepSeekChatResponse dto = objectMapper.readValue(rawResponse.getBody(), DeepSeekChatResponse.class);

        if (dto.getChoices() == null || dto.getChoices().isEmpty()
                || dto.getChoices().get(0).getMessage() == null
                || dto.getChoices().get(0).getMessage().getContent() == null) {
            throw new DeepSeekApiException("Невалидный ответ от DeepSeek API");
        }

        return dto.getChoices().get(0).getMessage().getContent();
    }
}
