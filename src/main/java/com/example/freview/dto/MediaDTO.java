package com.example.freview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaDTO {
    private Long id;
    private String title;
    private String overview;
    // Add other fields as necessary

}
