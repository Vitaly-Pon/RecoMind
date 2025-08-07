package com.example.demo.client;

import com.example.demo.config.DeepSeekConfig;
import com.example.demo.client.dto.DeepSeekChatRequest;
import com.example.demo.client.dto.DeepSeekChatResponse;
import com.example.demo.exception.DeepSeekApiException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Component
public class DeepSeekApiClient {
    private final String apiKey;
    private final String apiUrl;
    private final RestTemplate restTemplate;

    public DeepSeekApiClient(DeepSeekConfig config, RestTemplate restTemplate){
        this.apiKey = config.getKey();
        this.apiUrl = config.getUrl();
        this.restTemplate = restTemplate;
    }

    public DeepSeekChatResponse getRecommendations(DeepSeekChatRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<DeepSeekChatResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                DeepSeekChatResponse.class
        );
        if(response.getStatusCode() != HttpStatus.OK || response.getBody() == null){
            throw new DeepSeekApiException("Ошибка в запросе к DeepSeek API. Код ошибки: " + response.getStatusCode());
        }else {
            return response.getBody();
        }

    }
}
