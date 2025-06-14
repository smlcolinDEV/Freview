package com.example.freview.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {

    @NotNull(message = "userId ne peut pas être nul")
    private Long userId;
}