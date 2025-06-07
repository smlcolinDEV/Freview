package com.example.freview.dto;

public class UpdatedReviewRequestDTO {
    private Long id;
    private String Comment;

    public UpdatedReviewRequestDTO(Long id, String comment) {
        this.id = id;
        Comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
