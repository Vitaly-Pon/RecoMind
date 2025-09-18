package com.example.demo;

import com.example.demo.deepseek.DeepSeekApiClient;
import com.example.demo.service.MovieRecommendationService;
import com.example.demo.service.UserMovieRatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Transactional // откат данных после каждого теста
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MovieRecommendationService service;
    @Autowired
    protected UserMovieRatingService userMovieRatingService;
    @MockitoBean
    protected DeepSeekApiClient deepSeekApiClient;
}
