package com.example.springcinemawebapp.controller;

import com.example.springcinemawebapp.dto.MovieSessionDTO;
import com.example.springcinemawebapp.exception.MovieSessionTimeException;
import com.example.springcinemawebapp.exception.TimeOverlappingException;
import com.example.springcinemawebapp.model.MovieSession;
import com.example.springcinemawebapp.model.Role;
import com.example.springcinemawebapp.service.MovieSessionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.springcinemawebapp.properties.TechnicalConstants.*;
import static com.example.springcinemawebapp.properties.TechnicalTextConstants.DEFAULT_EXCEPTION_MSG;
import static com.example.springcinemawebapp.properties.TechnicalTextConstants.NO_SUCH_FIELD_MSG;


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

        if (bindingResult.hasErrors()) {
            return "admin/add-session";
        }

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
    public String getMovieSessions(@RequestParam(value = "userMode",
            required = false, defaultValue = "false") boolean userMode,
                                   Authentication authentication, Model model) {

        if (authentication != null && authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority(Role.ADMIN.name())) && !userMode) {
            return getPaginatedMovieSessions(1, "id", "asc", model);
        }
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
        LocalTime currentTime = LocalTime.now();
        model.addAttribute("currentTime", currentTime);

        return "sessions";
    }

    @GetMapping("/page/{page}")
    public String getPaginatedMovieSessions(@PathVariable(value = "page") int pageNumber,
                                     @RequestParam(value = "sortBy", required = false) String sortBy,
                                     @RequestParam(value = "sortDir", required = false) String sortDir,
                                     Model model) {
        if (sortBy == null) {
            sortBy = "id";
        }
        if (sortDir == null) {
            sortDir = "asc";
        }
        try {
            MovieSession.class.getDeclaredField(sortBy);
        } catch (NoSuchFieldException e) {
            log.error(NO_SUCH_FIELD_MSG + e.getMessage());
            sortBy = "id";
        }

        Page<MovieSession> page = movieSessionService.getPaginated(pageNumber, PAGE_SIZE, sortBy, sortDir);
        List<MovieSession> sessions = page.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("sessions", sessions);
        log.info(sessions);
        return "admin/sessions";
    }

    @DeleteMapping("/{id}")
    public String deleteMovieSession(@PathVariable(value = "id") long id) {
        try {
            movieSessionService.removeById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/sessions";
        }
        return "redirect:/sessions?delete-success";
    }

    @GetMapping("/{id}")
    public String updateMovieSessionForm(@PathVariable(value = "id") long id, Model model) {
        MovieSession movieSession;
        try {
            movieSession = movieSessionService.getById(id);
            log.info(movieSession);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/sessions";
        }
        MovieSessionDTO movieSessionDTO = MovieSessionDTO.builder()
                .movieId(movieSession.getMovie().getId())
                .start(movieSession.getStart())
                .date(movieSession.getDate())
                .build();
        model.addAttribute("movieSession", movieSessionDTO);
        return "admin/update-session";
    }

    @PostMapping("/{id}")
    public String updateMovieSession(@PathVariable(value = "id") long id,
                              @ModelAttribute("movieSession") @Valid MovieSessionDTO movieSession,
                              BindingResult bindingResult) {
        log.info(movieSession);
        log.info(bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/update-session";
        }

        try {
            movieSessionService.updateSession(id, movieSession);
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
            return "admin/update-session";
        }

        return "redirect:?update-success";
    }
}
