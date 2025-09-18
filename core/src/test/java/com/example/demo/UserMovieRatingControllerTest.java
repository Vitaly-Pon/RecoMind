package com.example.demo;

import com.example.demo.controller.request.UserMovieRatingRequest;
import com.example.demo.controller.request.UserRegistrationRequest;
import com.example.demo.repository.entity.MovieRating;
import com.example.demo.service.UserMovieRatingService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ActiveProfiles("test")
public class UserMovieRatingControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void cleanData() {
        // удаляем всех пользователей перед каждым тестом
        userService.getAllUsers().forEach(u -> userService.deleteUser(u.getId()));
    }

    @Test
    void shouldFailValidationIfTitleIsBlank() throws Exception {
        // создаем пользователя
        Long userId = userService.register(new UserRegistrationRequest("alex", "password")).getId();

        UserMovieRatingRequest request = new UserMovieRatingRequest();
        request.setUserId(userId);
        request.setTitle(""); // пустой заголовок
        request.setRating(new BigDecimal("5.0"));

        mockMvc.perform(post("/movie/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.title", is("title must not be blank")));
    }

    @Test
    void shouldSaveMovieRatingSuccessfully() throws Exception {
        // создаем пользователя
        Long userId = userService.register(new UserRegistrationRequest("alex", "password")).getId();

        UserMovieRatingRequest request = new UserMovieRatingRequest();
        request.setUserId(userId);
        request.setTitle("Фильм 1");
        request.setRating(new BigDecimal("8.0"));

        mockMvc.perform(post("/movie/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moviesRatings.id", notNullValue()))
                .andExpect(jsonPath("$.moviesRatings.rating", is(8.0)));
    }
}