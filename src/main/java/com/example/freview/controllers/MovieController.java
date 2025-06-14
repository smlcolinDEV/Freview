package com.example.freview.controllers;

import com.example.freview.dto.MovieDTO;
import com.example.freview.services.GetMediaFromTmdbService;
import com.example.freview.services.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final GetMediaFromTmdbService getMediaFromTmdbService;

    public MovieController(MovieService movieService, GetMediaFromTmdbService getMediaFromTmdbService) {
        this.movieService = movieService;
        this.getMediaFromTmdbService = getMediaFromTmdbService;
    }

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMoviesDTO();
    }

    @GetMapping("/popular")
    public List<MovieDTO> getPopularMovies() {
        return getMediaFromTmdbService.getPopularMovies();
    }

    @PostMapping
    public MovieDTO addMovie(@RequestBody MovieDTO movieDTO) {
        return movieService.addMovie(movieDTO);
    }

    @PutMapping
    public MovieDTO updateMovie(@RequestBody MovieDTO movieDTO) {
        return movieService.updateMovie(movieDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}
