package com.example.freview.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends Media {
    @NotNull
    private Integer runtime;


    public Movie(String title, String description, Integer runtime) {
        super(title, description);
        this.runtime = runtime;
    }

}
