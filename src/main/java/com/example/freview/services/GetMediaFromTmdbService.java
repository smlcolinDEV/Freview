package com.example.freview.services;

import com.example.freview.dto.MovieDTO;
import com.example.freview.models.Movie;
import com.example.freview.models.Serie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetMediaFromTmdbService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    @Value("${tmdb.api.key}")
    private String API_KEY;

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
    public MovieDTO fetchMovie(Long id) {
        String url = BASE_URL + "/movie/" + id + "?api_key=" + API_KEY;
        Movie m = restTemplate.getForObject(url, Movie.class);

        if (m == null) {
            return new MovieDTO(0L, "Not found", "Not found", 0);
        }
        return new MovieDTO(
                m.getId(),
                m.getTitle(),
                m.getOverview(),
                m.getRuntime() != null ? m.getRuntime() : 0
        );
    }
    public Serie fetchSerie(Long serieId) {
        String url = BASE_URL + "/tv/" + serieId + "?api_key=" + API_KEY;
        return restTemplate.getForObject(url, Serie.class);
    }
}
