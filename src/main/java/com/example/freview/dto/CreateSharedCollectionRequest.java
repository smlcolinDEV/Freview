package com.example.freview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSharedCollectionRequest {

    @NotBlank(message = "Le nom de la collection est obligatoire")
    private String title;
}