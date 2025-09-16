package com.example.demo.repository;

import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.UserMovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, Long> {
    List<UserMovieRating> findAllByUser(User user); // User, а не userId
    Optional<UserMovieRating> findByUserAndMovieTitle(User user, String movieTitle);
}