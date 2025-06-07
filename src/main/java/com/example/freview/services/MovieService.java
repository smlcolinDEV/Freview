package com.example.freview.services;

import com.example.freview.models.Movie;
import com.example.freview.repositories.MovieRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final String BASE_URL = "https://api.themoviedb.org/3";
    @Value("${tmdb.api.key}")
    private String API_KEY;
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public String getPopularMovies() {
        String url = BASE_URL + "/discover/movie?api_key=" + API_KEY + "&sort_by=popularity.desc";
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> movies = (List<Map<String, Object>>) jsonMap.get("results");

            ArrayNode filteredMoviesArray = objectMapper.createArrayNode();

            // Limit to the first 20 movies
            int limit = Math.min(movies.size(), 20);
            for (int i = 0; i < limit; i++) {
                Map<String, Object> movie = movies.get(i);
                ObjectNode filteredMovieNode = objectMapper.createObjectNode();
                filteredMovieNode.put("id", (Integer) movie.get("id"));
                filteredMovieNode.put("original_title", (String) movie.get("original_title"));
                filteredMovieNode.put("overview", (String) movie.get("overview"));
                filteredMoviesArray.add(filteredMovieNode);
            }

            ObjectNode resultNode = objectMapper.createObjectNode();
            resultNode.set("movies", filteredMoviesArray);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultNode);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing movie data: " + e.getMessage();
        }
    }



}
