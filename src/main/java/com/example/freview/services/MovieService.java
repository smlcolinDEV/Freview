package com.example.freview.services;

import com.example.freview.dto.MovieDTO;
import com.example.freview.models.Movie;
import com.example.freview.repositories.MovieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Value("${tmdb.api.key}")
    private String API_KEY;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> getAllMoviesDTO() {
        return movieRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<MovieDTO> getPopularMovies() {
        List<MovieDTO> popularMovies = new ArrayList<>();
        try {
            String url = BASE_URL + "/discover/movie?api_key=" + API_KEY + "&sort_by=popularity.desc";
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            for (JsonNode movie : results) {
                MovieDTO movieDTO = new MovieDTO(
                        movie.path("id").asLong(),
                        movie.path("original_title").asText(),
                        movie.path("overview").asText(),
                        movie.path("runtime").asInt(0)
                );
                popularMovies.add(movieDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return popularMovies.stream().limit(20).collect(Collectors.toList());
    }

    public MovieDTO addMovie(MovieDTO movieDTO) {
        Movie movie = convertToEntity(movieDTO);
        Movie savedMovie = movieRepository.save(movie);
        return convertToDto(savedMovie);
    }

    public MovieDTO updateMovie(MovieDTO movieDTO) {
        Movie movie = convertToEntity(movieDTO);
        Movie updatedMovie = movieRepository.save(movie);
        return convertToDto(updatedMovie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public MovieDTO fetchMovie(Long id) {
        try {
            String url = BASE_URL + "/movie/" + id + "?api_key=" + API_KEY;
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            MovieDTO movieDTO = new MovieDTO(
                    root.path("id").asLong(),
                    root.path("title").asText(),
                    root.path("overview").asText(),
                    root.path("runtime").asInt(0)
            );

            return movieDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return new MovieDTO(0L, "Not found", "Not found", 0);
        }
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
