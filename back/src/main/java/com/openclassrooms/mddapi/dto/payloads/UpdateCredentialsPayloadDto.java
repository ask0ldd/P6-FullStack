package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCredentialsPayloadDto {
    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
}
