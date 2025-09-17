package com.example.demo.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_movie_rating",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "movie_title"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "genre")
    private String genre;//todo убрать

    @Column(name = "emotion")
    private String emotion;//todo убрать

    @Column(name = "movie_title")
    private String movieTitle;//todo убрать

    private Movie movie;

    @Column(name = "director")
    private String director;//todo убрать

    @Column(name = "movie_year")
    private Integer movieYear;//todo убрать

    @Column(name = "rating", nullable = false, precision = 3, scale = 1) // теперь BigDecimal с 1 знаком после запятой
    private BigDecimal rating;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;
}