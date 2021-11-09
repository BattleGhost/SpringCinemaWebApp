package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.model.Movie;
import com.example.springcinemawebapp.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private MovieService service;

    @Test
    public void getAllShouldReturnAllMovies() {
        List<Movie> allMovies = List.of(Movie.builder().title("Movie 1").build(),
                Movie.builder().title("Movie 2").build());
        Mockito.when(repository.findAll()).thenReturn(allMovies);

        List<Movie> result = service.getAll();

        assertEquals(allMovies, result);
    }

    @Test
    public void getByIdShouldReturnMovieById() {
       long id = 1;
       String title = "Movie 1";
       Movie movie = Movie.builder().id(id).title(title).build();

       Mockito.when(repository.getById(id)).thenReturn(movie);

       Movie result = service.getById(id);
       assertEquals(movie, result);
    }
}