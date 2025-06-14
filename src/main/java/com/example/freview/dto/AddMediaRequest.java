package com.example.freview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMediaRequest {

    @NotNull
    private Long mediaId;

    @NotBlank
    private String type; // "movie" or "serie"
}