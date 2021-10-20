package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.dto.MovieDTO;
import com.example.springcinemawebapp.model.Movie;
import com.example.springcinemawebapp.repository.MovieRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public void addMovie(MovieDTO movie) {
        Movie movieToAdd = Movie.builder()
                .title(movie.getTitle())
                .duration(movie.getDuration())
                .description(movie.getDescription())
                .build();
        repository.save(movieToAdd);
    }

    public void updateMovie(long id, MovieDTO movie) {
        Movie movieToUpdate = Movie.builder()
                .id(id)
                .title(movie.getTitle())
                .duration(movie.getDuration())
                .description(movie.getDescription())
                .build();
        repository.save(movieToUpdate);
    }

    public List<Movie> getAll() {
        return repository.findAll();
    }
    public List<Movie> getAll(String filter) {
        return repository.findAllByTitleContains(filter);
    }

    public Page<Movie> getPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return repository.findAll(pageable);
    }

    public void removeById(long id) {
        repository.deleteById(id);
    }

    public Movie getById(long id) {
        return repository.getById(id);
    }
}
