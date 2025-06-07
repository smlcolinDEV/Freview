package com.example.freview.services;

import com.example.freview.models.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public TmdbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Movie fetchMediaDetails(int mediaId) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d?api_key=%s", mediaId, apiKey);
        Map<String, Object> tmdbMedia = restTemplate.getForObject(url, Map.class);

        // Create a new Movie object and set its properties
        Movie movie = new Movie();
        movie.setTitle((String) tmdbMedia.get("title"));
        movie.setDescription((String) tmdbMedia.get("overview"));

        // Set other properties as necessary

        return movie;
    }
}
