package com.example.springcinemawebapp.service;

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

    public void addMovie(Movie movieToAdd) {
        repository.save(movieToAdd);
    }

    public List<Movie> getAll() {
        return repository.findAll();
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
}
