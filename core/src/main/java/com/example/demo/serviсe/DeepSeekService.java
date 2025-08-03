package com.example.demo.serviсe;

import com.example.demo.controller.dto.request.RecommendationRequest;
import com.example.demo.controller.dto.response.RecommendationResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DeepSeekService {
    private final String apiKey;
    private final String apiUrl;
    private final RestTemplate restTemplate;

    public DeepSeekService() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.apiKey = dotenv.get("DEEPSEEK_API_KEY");
        this.apiUrl = "https://api.deepseek.com/v1/chat/completions";
        this.restTemplate = new RestTemplate();
    }

    public String getMovieRecommendation(String userPrompt) {
        RecommendationRequest request = new RecommendationRequest();
        request.setMessages(List.of(
                new RecommendationRequest.Message("user",
                        "Порекомендуй фильмы в жанре: " + userPrompt + ". " +
                                "Формат: Название (Год, Режиссер) — краткое описание.")
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<RecommendationResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                RecommendationResponse.class
        );

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}
