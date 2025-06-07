package com.example.freview.dto;

import com.example.freview.enums.WatchedStatus;

public class ReviewDTO {
    private Long id;
    private String comment;
    private Double rating;
    private WatchedStatus status;


    public ReviewDTO(Long id, String comment, Double rating, WatchedStatus status) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public WatchedStatus getStatus() {
        return status;
    }
    public void setStatus(WatchedStatus status) {
        this.status = status;
    }
}
