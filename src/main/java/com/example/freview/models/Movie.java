package com.example.freview.models;

import com.example.freview.enums.WatchedStatus;
import jakarta.persistence.Entity;

@Entity
public class Movie extends Media {
    private Integer runtime;

    public Movie() {
    }

    public Movie(String title, String description, Integer runtime) {
        super(title, description);
        this.runtime = runtime;
    }

    // Getters and Setters
    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }
}
