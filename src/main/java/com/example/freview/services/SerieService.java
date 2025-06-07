package com.example.freview.services;

import com.example.freview.repositories.SerieRepository;
import org.springframework.stereotype.Service;

@Service
public class SerieService {
    private final SerieRepository serieRepository;
    public SerieService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }
}
