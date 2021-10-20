package com.example.springcinemawebapp.repository;

import com.example.springcinemawebapp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByTitleContains(String title);
}
