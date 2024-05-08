package com.openclassrooms.mddapi.dto.reponses;

import lombok.Data;

@Data
public class DefaultResponseDto {

    private String message;

    public DefaultResponseDto(String message) {
        this.message = message;
    }
}
