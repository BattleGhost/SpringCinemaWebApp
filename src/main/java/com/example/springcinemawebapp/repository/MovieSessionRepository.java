package com.example.springcinemawebapp.repository;

import com.example.springcinemawebapp.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {
}
