package com.example.freview.dto;

public class MediaDTO {
    private Long id;
    private String mediaName;
    // Add other fields as necessary

    // Constructors, getters, and setters
    public MediaDTO() {
    }

    public MediaDTO(Long id, String mediaName) {
        this.id = id;
        this.mediaName = mediaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }
}
