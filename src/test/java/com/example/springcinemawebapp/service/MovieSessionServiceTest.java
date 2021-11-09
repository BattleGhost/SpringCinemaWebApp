package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.model.Movie;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.repository.MovieSessionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieSessionServiceTest {

    @Mock
    private MovieSessionRepository repository;

    @InjectMocks
    private MovieSessionService service;

    @Test
    public void getByIdShouldReturnMovieSessionById() {
        long id = 1;
        String title = "Movie 1";
        MovieSession session = MovieSession.builder().id(id).movie(Movie.builder().title(title).build()).build();

        Mockito.when(repository.getById(id)).thenReturn(session);

        MovieSession result = service.getById(id);
        assertEquals(session, result);
    }

    @Test
    public void getByDateShouldReturnAllSessionsByDate() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().plusDays(1);

        List<MovieSession> sessionsByDate = Stream.of(
                MovieSession.builder().date(date1).build(),
                MovieSession.builder().date(date1).build(),
                MovieSession.builder().date(date2).build())
                .filter(session -> session.getDate().isEqual(date1)).collect(Collectors.toList());

        Mockito.when(repository.getAllByDate(date1)).thenReturn(
                List.of(MovieSession.builder().date(date1).build(),
                MovieSession.builder().date(date1).build()));

        List<MovieSession> result = service.getByDate(date1);

        assertEquals(sessionsByDate, result);
    }

    @Test
    public void getByDateAfterShouldReturnAllSessionsByDateAfter() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().plusDays(1);
        LocalDate date3 = LocalDate.now().minusDays(1);

        List<MovieSession> sessionsByDateAfter = Stream.of(
                        MovieSession.builder().date(date1).build(),
                        MovieSession.builder().date(date2).build(),
                        MovieSession.builder().date(date3).build())
                .filter(session -> session.getDate().isAfter(date1)).collect(Collectors.toList());

        Mockito.when(repository.getAllByDateAfter(date1)).thenReturn(List.of(MovieSession.builder().date(date2).build()));

        List<MovieSession> result = service.getByDateAfter(date1);

        assertEquals(sessionsByDateAfter, result);
    }

    @Test
    public void getByDateBetweenShouldReturnAllSessionsByDateBetween() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now().plusDays(1);
        LocalDate date3 = LocalDate.now().minusDays(1);

        List<MovieSession> sessionsByDateBetween = Stream.of(
                        MovieSession.builder().date(date1).build(),
                        MovieSession.builder().date(date2).build(),
                        MovieSession.builder().date(date3).build())
                .filter(session -> session.getDate().isAfter(date3) && session.getDate().isBefore(date2)).collect(Collectors.toList());

        Mockito.when(repository.getAllByDateBetween(date2, date3)).thenReturn(List.of(MovieSession.builder().date(date1).build()));

        List<MovieSession> result = service.getByDateBetween(date2, date3);

        assertEquals(sessionsByDateBetween, result);
    }
}