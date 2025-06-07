package com.example.freview.models;

import com.example.freview.enums.WatchedStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public Review() {
    }

    public Review( String comment, Double rating, LocalDate createdDate, WatchedStatus watchedStatus, User user, Media media) {
        this.comment = comment;
        this.rating = rating;
        this.createdDate = createdDate;
        this.watchedStatus = watchedStatus;
        this.user = user;
        this.media = media;
    }
    public Review(WatchedStatus watchedStatus){
        this.watchedStatus = watchedStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String review) {
        this.comment = review;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    public Media getMedia() {
        return media;
    }
    public void setMedia(Media media) {
        this.media = media;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public WatchedStatus getWatchedStatus() {
        return watchedStatus;
    }
    public void setWatchedStatus(WatchedStatus watchedStatus) {
        this.watchedStatus = watchedStatus;
    }

    public SharedCollection getSharedCollection() {
        return sharedCollection;
    }
    public void setSharedCollection(SharedCollection sharedCollection) {
        this.sharedCollection = sharedCollection;
    }

}