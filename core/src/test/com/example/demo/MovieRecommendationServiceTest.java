package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

//@SpringBootTest
public class MovieRecommendationServiceTest {

//    @MockitoBean
//    private DeepSeekApiClient apiClient;
//
//    @Autowired
//    private MovieRecommendationService service;

    @Test
    void shouldReturnRecommendation(){
//
//        var mockResponse = new DeepSeekChatResponse();
//
//        ChatMessageResponse message = new ChatMessageResponse();
//        message.setContent("\"Фильм 1\\n\\nФильм 2\"");
//
//        RecommendationChoice choice = new RecommendationChoice();
//        choice.setMessage(message);
//
//        mockResponse.setChoices(List.of(choice));
//
//        when(apiClient.getRecommendations(any())).thenReturn(mockResponse);
//
//        MovieRecommendationsResponse responseResult = service.getMovieRecommendations("Комедия",2);
//
//        assertThat(responseResult.getRecommendations()).containsExactly("Фильм 1", "Фильм 2");
//
//        verify(apiClient, times(1)).getRecommendations(any());
    }
}
