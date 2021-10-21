package com.example.springcinemawebapp.repository;

import com.example.springcinemawebapp.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long> {
    List<MovieSession> getAllByDate(LocalDate date);
    List<MovieSession> getAllByDateAfter(LocalDate date);
    List<MovieSession> getAllByDateBetween(LocalDate date1, LocalDate date2);
}
