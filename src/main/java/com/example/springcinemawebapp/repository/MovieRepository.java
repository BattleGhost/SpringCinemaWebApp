package com.example.springcinemawebapp.repository;

import com.example.springcinemawebapp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
