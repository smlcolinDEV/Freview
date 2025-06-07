package com.example.freview.models;

import com.example.freview.enums.WatchedStatus;
import jakarta.persistence.Entity;


@Entity
public class Serie extends Media{
    private Integer seasonCount;

    public Serie(){

    }
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
