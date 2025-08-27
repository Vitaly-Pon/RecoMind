package com.example.demo;

import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//public class UserControllerImpTest extends AbstractControllerTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void shouldRegisterUserSuccessfully() throws Exception {
//        UserRegistrationRequest request = UserRegistrationRequest.builder()
//                .username("new_user")
//                .password("strongPass")
//                .build();
//
//        mockMvc.perform(post("/users/register") // предполагаю, что у тебя эндпоинт такой
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is("new_user")));
//    }
//}
