package com.example.freview.dto;

public class UserDTO {
    private Long id;
    private String userName;
    // Add other fields as necessary

    // Constructors, getters, and setters
    public UserDTO() {
    }

    public UserDTO(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
