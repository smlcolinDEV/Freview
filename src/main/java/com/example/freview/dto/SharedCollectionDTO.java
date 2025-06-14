package com.example.freview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SharedCollectionDTO {
    private Long id;
    private String name;
    private List<MediaDTO> mediaList;
    private List<UserDTO> users;
    private List<ReviewDTO> reviews;


    public SharedCollectionDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public SharedCollectionDTO(Long id, String name, List<MediaDTO> mediaDTOs, List<UserDTO> userDTOs) {
        this.id = id;
        this.name = name;
        this.mediaList = mediaDTOs;
        this.users = userDTOs;
    }
}