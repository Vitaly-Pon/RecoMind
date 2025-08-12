package com.example.demo;

import com.example.demo.controller.dto.request.BaseMovieRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BaseMovieRequestTest {

    static class TestMovieRequest extends BaseMovieRequest{};

    @Test
    void shouldReturnDefaultCount(){
        TestMovieRequest request = new TestMovieRequest();
        request.setCount(null);

        int count = request.getCountOrDefault();

        assertThat(count).isEqualTo(5);
    }
    @Test
    void shouldReturnCountWhenCountIsSet(){
        TestMovieRequest request = new TestMovieRequest();
        request.setCount(2);

        int count = request.getCountOrDefault();

        assertThat(count).isEqualTo(2);
    }
}
