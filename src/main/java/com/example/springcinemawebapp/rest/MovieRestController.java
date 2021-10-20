package com.example.springcinemawebapp.rest;

import com.example.springcinemawebapp.model.Movie;
import com.example.springcinemawebapp.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieRestController {
    private final MovieService movieService;

    @GetMapping
    public List<Movie> getAllFiltered(@RequestParam(value = "filter") String filter) {
        return movieService.getAll(filter);
    }
}
