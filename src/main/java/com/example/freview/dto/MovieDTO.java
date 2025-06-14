package com.example.freview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long id;

    @NotBlank(message = "Le titre ne peut pas être vide")
    private String title;

    private String overview;

    private Integer runtime;
}
