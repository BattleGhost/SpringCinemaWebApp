package com.example.springcinemawebapp.controller;

import com.example.springcinemawebapp.dto.MovieSessionDTO;
import com.example.springcinemawebapp.exception.MovieSessionTimeException;
import com.example.springcinemawebapp.exception.TimeOverlappingException;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.service.MovieSessionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.springcinemawebapp.properties.TechnicalConstants.MOVIE_SESSIONS_END_TIME;
import static com.example.springcinemawebapp.properties.TechnicalConstants.MOVIE_SESSIONS_START_TIME;
import static com.example.springcinemawebapp.properties.TechnicalTextConstants.DEFAULT_EXCEPTION_MSG;


@Log4j2
@Controller
@RequestMapping("/sessions")
@AllArgsConstructor
public class MovieSessionController {

    private final MovieSessionService movieSessionService;
    private MessageSource messageSource;

    @GetMapping("/add")
    public String addSessionForm(@ModelAttribute("movieSession") MovieSessionDTO movieSession) {
        return "admin/add-session";
    }

    @PostMapping("/add")
    public String addSession(@ModelAttribute("movieSession") @Valid MovieSessionDTO movieSession,
                             BindingResult bindingResult) {
        log.info(movieSession);
        log.info(bindingResult);

        try {
            movieSessionService.addSession(movieSession);
        } catch (MovieSessionTimeException e) {
            String message;
            try {
                message = messageSource.getMessage("message.wrong.time.bounds",
                        new Object[] {MOVIE_SESSIONS_START_TIME, MOVIE_SESSIONS_END_TIME},
                        LocaleContextHolder.getLocale());
            } catch (NoSuchMessageException ie) {
                log.info(ie.getMessage());
                message = DEFAULT_EXCEPTION_MSG;
            }
            bindingResult.rejectValue("start", "error.movieSession", message);

        } catch (TimeOverlappingException e) {
            String message;
            try {
                message = messageSource.getMessage("message.wrong.time.overlapping",
                        null, LocaleContextHolder.getLocale());
            } catch (NoSuchMessageException ie) {
                log.info(ie.getMessage());
                message = DEFAULT_EXCEPTION_MSG;
            }
            bindingResult.rejectValue("start", "error.movieSession", message);

        } catch (Exception e) {
            String message;
            try {
                message = messageSource.getMessage("message.wrong.movie.id",
                        null, LocaleContextHolder.getLocale());
            } catch (NoSuchMessageException ie) {
                log.info(ie.getMessage());
                message = DEFAULT_EXCEPTION_MSG;
            }
            bindingResult.rejectValue("movieId", "error.movieSession", message);
        }
        if (bindingResult.hasErrors()) {
            return "admin/add-session";
        }

        return "redirect:add?success";
    }

    @GetMapping
    public String userGetSessions(Model model) {
        LocalDate today = LocalDate.now();
        List<MovieSession> sessions = movieSessionService.getByDateBetween(today, today.plusDays(7));
        log.info("Sessions: " + sessions);
        for (int i = 0; i < 7; i++) {
            int plusDays = i;
            model.addAttribute("day"+(i+1)+"Schedule",
                    sessions.stream()
                            .filter(session -> session.getDate().isEqual(today.plusDays(plusDays)))
                            .collect(Collectors.toList())
            );
        }


        return "sessions";
    }
}
