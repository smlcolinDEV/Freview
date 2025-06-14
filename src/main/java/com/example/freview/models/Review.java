package com.example.freview.models;

import com.example.freview.enums.WatchedStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    private String comment;
    private Double rating;
    private LocalDate createdDate;
    private WatchedStatus watchedStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @ManyToOne
    @JoinColumn(name = "shared_collection_id")
    private SharedCollection sharedCollection;

    public Review(String comment, Double rating, LocalDate createdDate, WatchedStatus watchedStatus, User user, Media media) {
        this.comment = comment;
        this.rating = rating;
        this.createdDate = createdDate;
        this.watchedStatus = watchedStatus;
        this.user = user;
        this.media = media;
    }


}