package com.example.freview.models;

import com.example.freview.enums.WatchedStatus;
import jakarta.persistence.*;

@Entity
public class UserMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @Enumerated(EnumType.STRING)
    private WatchedStatus watchedStatus;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    // Constructors, getters, and setters
    public UserMedia() {
    }

    public UserMedia(User user, Media media, WatchedStatus watchedStatus, Review review) {
        this.user = user;
        this.media = media;
        this.watchedStatus = watchedStatus;
        this.review = review;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public WatchedStatus getWatchedStatus() {
        return watchedStatus;
    }

    public void setWatchedStatus(WatchedStatus watchedStatus) {
        this.watchedStatus = watchedStatus;
    }
    public Review getReview() {
        return review;
    }
    public void setReview(Review review) {
        this.review = review;
    }
}
