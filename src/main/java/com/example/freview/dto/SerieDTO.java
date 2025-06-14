package com.example.freview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SerieDTO {
    private Long id;

    @NotBlank(message = "Le titre ne peut pas être vide")
    private String title;

    private String overview;

    private Integer seasonCount;
}
