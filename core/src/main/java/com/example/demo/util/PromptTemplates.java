package com.example.demo.util;

public class PromptTemplates {
    public static String movieRecommendationPrompt(String genre, int count){
        return String.format(
                "Порекомендуй ровно %d лучших фильмов в жанре '%s'. " +
                "Формат для каждого: Название (Год, Режиссер) — краткое описание. " +
                "Без вводных слов.", count, genre);
    }

    public static String movieRecommendationOnEmotionPrompt(String emotion, int count){
        return String.format(
                "Порекомендуй ровно %d лучших фильмов с настроением '%s'. " +
                        "Формат для каждого: Название (Год, Режиссер) — краткое описание. " +
                        "Без вводных слов.", count, emotion);
    }
}
