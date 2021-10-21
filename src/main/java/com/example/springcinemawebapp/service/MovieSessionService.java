package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.dto.MovieSessionDTO;
import com.example.springcinemawebapp.exception.MovieSessionTimeException;
import com.example.springcinemawebapp.exception.TimeOverlappingException;
import com.example.springcinemawebapp.model.Movie;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.repository.MovieSessionRepository;
import com.example.springcinemawebapp.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.springcinemawebapp.properties.TechnicalConstants.MOVIE_SESSIONS_END_TIME;
import static com.example.springcinemawebapp.properties.TechnicalConstants.MOVIE_SESSIONS_START_TIME;
import static com.example.springcinemawebapp.properties.TechnicalTextConstants.TIME_OVERLAPPING_MSG;
import static com.example.springcinemawebapp.properties.TechnicalTextConstants.WRONG_MOVIE_SESSION_TIME_MSG;

@Service
@AllArgsConstructor
public class MovieSessionService {
    private final MovieSessionRepository repository;
    private final MovieService movieService;

    @Transactional
    public void addSession(MovieSessionDTO movieSession) {

        Movie movie = movieService.getById(movieSession.getMovieId());

        MovieSession session = MovieSession.builder()
                .date(movieSession.getDate())
                .start(movieSession.getStart())
                .movie(movie)
                .build();

        LocalTime start = session.getStart();
        LocalTime end = session.getEnd();

        if (start.isBefore(MOVIE_SESSIONS_START_TIME) || start.isAfter(MOVIE_SESSIONS_END_TIME)) {
            throw new MovieSessionTimeException(WRONG_MOVIE_SESSION_TIME_MSG);
        }

        List<MovieSession> sessions = repository.getAllByDate(session.getDate());
        for (MovieSession s: sessions) {
            if (DateTimeUtils.isTimeOverlapping(start, end, s.getStart(), s.getEnd())) {
                throw new TimeOverlappingException(TIME_OVERLAPPING_MSG);
            }
        }
        repository.save(session);
    }

    public MovieSession getById(long id) {
        return repository.getById(id);
    }

    public List<MovieSession> getByDate(LocalDate date) {
        return repository.getAllByDate(date);
    }

    public List<MovieSession> getByDateAfter(LocalDate date) {
        return repository.getAllByDateAfter(date);
    }

    public List<MovieSession> getByDateBetween(LocalDate date1, LocalDate date2) {
        return repository.getAllByDateBetween(date1, date2);
    }
}
