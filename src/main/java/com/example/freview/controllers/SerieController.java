package com.example.freview.controllers;

import com.example.freview.dto.SerieDTO;
import com.example.freview.models.Serie;
import com.example.freview.services.SerieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/serie")
public class SerieController {
    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping
    public List<Serie> getAllSeries() {
        return serieService.getAllMovies();
    }

    @GetMapping("/popular")
    public List<SerieDTO> getPopularSeries() {
        return serieService.getPopularSeries();
    }

    @PostMapping
    public Serie addSerie(@RequestBody Serie serie) {
        return serieService.addSerie(serie);
    }

    @PutMapping
    public Serie updateSerie(@RequestBody Serie serie) {
        return serieService.updateSerie(serie);
    }

    @DeleteMapping
    public void deleteSerie(@PathVariable Long serieId) {
        serieService.deleteSerie(serieId);
    }

}
