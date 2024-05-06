package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCredentialsPayloadDto {
    @NotBlank(message = "Username can't be blank.")
    @Size(min = 6, max = 20, message = "Username length must be between 6 & 20.")
    private String username;

    @NotBlank(message = "Email can't be blank.")
    @Size(max = 50, message = "Email length can't exceed 50.")
    @Email(message = "Not a valid email format.")
    private String email;
}
