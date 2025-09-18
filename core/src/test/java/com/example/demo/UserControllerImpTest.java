package com.example.demo;

import com.example.demo.controller.request.UserRegistrationRequest;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerImpTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void cleanUsers() {
        userService.getAllUsers().forEach(u -> userService.deleteUser(u.getId()));
    }

    @Test
    void shouldFailValidationIfUsernameBlank() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("");
        request.setPassword("validPass");

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.username", is("Требуется ввести имя пользователя")));
    }

    @Test
    void shouldFailValidationIfPasswordTooShort() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("validUser");
        request.setPassword("123");

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.password", is("Пароль должен содержать более 5 символов")));
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("alex");
        request.setPassword("password");

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is("alex")))
                .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        // создаем пользователей через сервис
        userService.register(new UserRegistrationRequest("alex", "password"));
        userService.register(new UserRegistrationRequest("maria", "password"));

        mockMvc.perform(get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.username=='alex')]").exists())
                .andExpect(jsonPath("$[?(@.username=='maria')]").exists());
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        // создаем пользователя через сервис
        UserResponse user = userService.register(new UserRegistrationRequest("alex", "password"));

        // удаляем пользователя через контроллер
        mockMvc.perform(delete("/user/" + user.getId()))
                .andExpect(status().isNoContent());

        // проверяем, что таблица пользователей пуста
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}