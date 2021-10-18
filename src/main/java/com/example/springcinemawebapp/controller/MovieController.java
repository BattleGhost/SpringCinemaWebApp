package com.example.springcinemawebapp.controller;

import com.example.springcinemawebapp.dto.MovieDTO;
import com.example.springcinemawebapp.model.Movie;
import com.example.springcinemawebapp.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {

    private static final int PAGE_SIZE = 10;
    private final MovieService movieService;

    @GetMapping("/add")
    public String addMovieForm(@ModelAttribute("movie") MovieDTO movie) {
        return "admin/add-movie";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movie") @Valid MovieDTO movie, BindingResult bindingResult) {
        log.info(movie);
        log.info(bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/add-movie";
        }
        Movie movieToAdd = Movie.builder()
                .title(movie.getTitle())
                .duration(movie.getDuration())
                .description(movie.getDescription())
                .build();

        movieService.addMovie(movieToAdd);

        return "redirect:add?success";

    }

    @GetMapping
    public String getFirstPageMovies(Model model) {
        return getPaginatedMovies(1, "id", "asc", model);
    }

    @GetMapping("/page/{page}")
    public String getPaginatedMovies(@PathVariable(value = "page") int pageNumber,
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
            Movie.class.getDeclaredField(sortBy);
        } catch (NoSuchFieldException e) {
            log.error("No such field: " + e.getMessage());
            sortBy = "id";
        }
        Page<Movie> page = movieService.getPaginated(pageNumber, PAGE_SIZE, sortBy, sortDir);


        List<Movie> movies = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("movies", movies);
        log.info(movies);
        return "movies";
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable(value = "id") long id) {
        try {
            movieService.removeById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/movies";
        }
        return "redirect:/movies?delete-success";
    }

    @GetMapping("/{id}")
    public String updateMovieForm(@PathVariable(value = "id") long id, Model model) {
        Movie movie;
        try {
            movie = movieService.getById(id);
            log.info(movie);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/movies";
        }

        model.addAttribute("movie", movie);
        return "admin/update-movie";
    }

    @PostMapping("/{id}")
    public String updateMovie(@PathVariable(value = "id") long id,
                              @ModelAttribute("movie") @Valid MovieDTO movie,
                              BindingResult bindingResult) {
        log.info(movie);
        log.info(bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/update-movie";
        }
        Movie movieToUpdate = Movie.builder()
                .id(id)
                .title(movie.getTitle())
                .duration(movie.getDuration())
                .description(movie.getDescription())
                .build();
        movieService.addMovie(movieToUpdate);

        return "redirect:?update-success";
    }
}
