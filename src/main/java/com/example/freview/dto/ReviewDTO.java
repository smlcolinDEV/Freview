package com.example.freview.dto;

import com.example.freview.enums.WatchedStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private String comment;
    private Double rating;
    private WatchedStatus status;



}
