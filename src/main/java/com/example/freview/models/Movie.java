package com.example.freview.models;

import com.example.freview.enums.WatchedStatus;
import jakarta.persistence.Entity;
import lombok.*;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends Media {
    private Integer runtime;


    public Movie(String title, String description, Integer runtime) {
        super(title, description);
        this.runtime = runtime;
    }

}
