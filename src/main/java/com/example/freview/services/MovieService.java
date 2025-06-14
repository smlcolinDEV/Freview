package com.example.freview.services;

import com.example.freview.dto.MovieDTO;
import com.example.freview.models.Movie;
import com.example.freview.repositories.MovieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("$tmdb.api.key")
    private String API_KEY;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> getAllMoviesDTO() {
        return movieRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Transactional
    public MovieDTO addMovie(MovieDTO movieDTO) {
        Movie movie = convertToEntity(movieDTO);
        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
    }
    @Transactional
    public MovieDTO updateMovie(MovieDTO movieDTO) {
        Movie movie = convertToEntity(movieDTO);
        Movie updatedMovie = movieRepository.save(movie);
        return convertToDto(updatedMovie);
    }
    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }



    private MovieDTO convertToDto(Movie movie) {
        MovieDTO movieDTO = new MovieDTO(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getRuntime());
        return movieDTO;
    }

    private Movie convertToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setId(movieDTO.getId());
        movie.setTitle(movieDTO.getTitle());
        movie.setOverview(movieDTO.getOverview());
        movie.setRuntime(movieDTO.getRuntime());
        return movie;
    }
}
