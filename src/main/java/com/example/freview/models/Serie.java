package com.example.freview.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity

@NoArgsConstructor
@AllArgsConstructor
public class Serie extends Media{
    private Integer seasonCount;

    public Serie(String title, String description, Integer seasonCount) {
        super(title, description);
        this.seasonCount = seasonCount;
    }
    public Integer getSeasonCount() {
        return seasonCount;
    }
    public void setSeasonCount(Integer seasonCount) {
        this.seasonCount = seasonCount;
    }

}
