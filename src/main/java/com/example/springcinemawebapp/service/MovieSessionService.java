package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.dto.MovieSessionDTO;
import com.example.springcinemawebapp.exception.MovieSessionTimeException;
import com.example.springcinemawebapp.exception.TimeOverlappingException;
import com.example.springcinemawebapp.model.Movie;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.repository.MovieSessionRepository;
import com.example.springcinemawebapp.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.springcinemawebapp.properties.TechnicalConstants.*;
import static com.example.springcinemawebapp.properties.TechnicalTextConstants.TIME_OVERLAPPING_MSG;
import static com.example.springcinemawebapp.properties.TechnicalTextConstants.WRONG_MOVIE_SESSION_TIME_MSG;

@Log4j2
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

        List<MovieSession> sessions = repository.getAllByDate(session.getDate());
        addSession(session, sessions);
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

    public Page<MovieSession> getPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return repository.findAll(pageable);
    }

    public void removeById(long id) {
        repository.deleteById(id);
    }

    public void updateSession(long id, MovieSessionDTO movieSession) {
        Movie movie = movieService.getById(movieSession.getMovieId());

        MovieSession session = MovieSession.builder()
                .id(id)
                .date(movieSession.getDate())
                .start(movieSession.getStart())
                .movie(movie)
                .build();

        List<MovieSession> sessions = repository.getAllByDateAndIdIsNot(session.getDate(), id);
        addSession(session, sessions);
    }

    private void addSession(MovieSession session, List<MovieSession> sessions) {
        LocalTime start = session.getStart();
        LocalTime end = session.getEnd();

        if (start.isBefore(MOVIE_SESSIONS_START_TIME) || start.isAfter(MOVIE_SESSIONS_END_TIME)) {
            throw new MovieSessionTimeException(WRONG_MOVIE_SESSION_TIME_MSG);
        }

        for (MovieSession s: sessions) {
            if (DateTimeUtils.isTimeOverlapping(start, end, s.getStart(), s.getEnd())) {
                throw new TimeOverlappingException(TIME_OVERLAPPING_MSG);
            }
        }
        log.info("Session: " + session);
        repository.save(session);
    }
}
