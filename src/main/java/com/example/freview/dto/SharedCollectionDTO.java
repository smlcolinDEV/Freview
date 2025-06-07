package com.example.freview.dto;

import java.util.List;

public class SharedCollectionDTO {
    private Long id;
    private String title;
    private List<MediaDTO> mediaList;
    private List<UserDTO> users;

    // Constructors
    public SharedCollectionDTO() {
    }

    public SharedCollectionDTO(Long id, String title, List<MediaDTO> mediaList, List<UserDTO> users) {
        this.id = id;
        this.title = title;
        this.mediaList = mediaList;
        this.users = users;
    }

    public SharedCollectionDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MediaDTO> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaDTO> mediaList) {
        this.mediaList = mediaList;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}