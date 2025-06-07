package com.example.freview.dto;

public class UpdatedPasswordRequestDTO {
    private Long id;
    private String Password;

    public UpdatedPasswordRequestDTO(Long id, String password) {
        this.id = id;
        this.Password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
