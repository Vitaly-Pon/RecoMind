package com.example.demo.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Сущность хранящая все о фильмах кроме рейтинга
@Table(name = "movie",
        uniqueConstraints = @UniqueConstraint(columnNames = {"movie_title", "movie_year"}))
@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // обязательный PK

    @Column(name = "genre")
    private String genre;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "movie_title", nullable = false)
    private String movieTitle;

    @Column(name = "director")
    private String director;

    @Column(name = "movie_year")
    private Integer movieYear;
}
