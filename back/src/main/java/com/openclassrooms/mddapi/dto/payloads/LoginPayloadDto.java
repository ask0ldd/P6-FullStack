package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginPayloadDto {
    @NotBlank
    @Size(min = 6, max = 20)
    private String emailOrUsername;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
