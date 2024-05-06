package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginPayloadDto {
    @NotBlank(message = "Username / Email can't be blank.")
    @Size(min = 6, max = 50, message = "Username / Email length must be between 6 & 50.")
    private String emailOrUsername;

    @NotBlank(message = "Password can't be blank.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$", message = "Password must be at least 8 characters long and contain at least one number, one lowercase letter, one uppercase letter, and one special character.")
    @Size(min = 8, max = 40, message = "Password length must be between 8 & 40.")
    private String password;
}
