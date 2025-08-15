package com.example.demo;

import com.example.demo.controller.dto.response.ChatMessageResponse;
import com.example.demo.controller.dto.response.MovieRecommendationsResponse;
import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.deepseek.dto.DeepSeekChatResponse;
import com.example.demo.deepseek.dto.RecommendationChoice;
import com.example.demo.service.MovieRecommendationServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MovieRecommendationServiceImpTest {

}
