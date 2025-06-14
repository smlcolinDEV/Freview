package com.example.freview.services;

import com.example.freview.dto.MovieDTO;
import com.example.freview.dto.SerieDTO;
import com.example.freview.models.Serie;
import com.example.freview.repositories.SerieRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import com.example.freview.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SerieService {
    private final SerieRepository serieRepository;
    private final String BASE_URL = "https://api.themoviedb.org/3";
    private final RestTemplate restTemplate;
    @Value("${tmdb.api.key}")
    private String API_KEY;

    public SerieService(SerieRepository serieRepository, RestTemplate restTemplate) {
        this.serieRepository = serieRepository;
        this.restTemplate = restTemplate;
    }
    public List<SerieDTO> getPopularSeries() {
        List<SerieDTO> popularSeries = new ArrayList<>();
        try {
            String url = BASE_URL + "/discover/tv?api_key=" + API_KEY + "&sort_by=popularity.desc";
            String jsonResponse = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            for (JsonNode movie : results) {
                SerieDTO serieDTO = new SerieDTO(
                        movie.path("id").asLong(),
                        movie.path("original_name").asText(),
                        movie.path("overview").asText(),
                        movie.path("seasonCount").asInt(0)
                );
                popularSeries.add(serieDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return popularSeries.stream().limit(20).collect(Collectors.toList());
    }
    public Serie fetchSerie(@PathVariable("serieId") Long serieId) {
        String url = BASE_URL + "/tv/" + serieId + "?api_key=" + API_KEY;
        Map<String,Object> fetchedSerie = restTemplate.getForObject(url, Map.class);

        Serie serie = new Serie();
        serie.setTitle((String) fetchedSerie.get("original_name"));
        serie.setOverview((String) fetchedSerie.get("overview"));
        serie.setSeasonCount((Integer) fetchedSerie.get("number_of_seasons"));
        return serie;

    }
    public List<Serie> getAllMovies() {
        return serieRepository.findAll();
    }
    public Serie addSerie(@RequestBody Serie serie) {
        return serieRepository.save(serie);
    }
    public Serie updateSerie(@RequestBody Serie serie) {
        var existingSerie = serieRepository.findById(serie.getId());
        if(existingSerie.isPresent()){
            Serie updatedSerie = existingSerie.get();
            updatedSerie.setTitle(serie.getTitle());
            updatedSerie.setOverview(serie.getOverview());
            updatedSerie.setSeasonCount(serie.getSeasonCount());
            updatedSerie.setReviews(serie.getReviews());
            return serieRepository.save(updatedSerie);
        }
        else{
            throw new ResourceNotFoundException("Serie not found");
        }
    }
    public void deleteSerie(@PathVariable Long serieId) {
        serieRepository.deleteById(serieId);
    }

}
