package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterPayloadDto {
    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$", message = "Password must be at least 8 characters long and contain at least one number, one lowercase letter, one uppercase letter, and one special character.")
    @Size(min = 8, max = 40)
    private String password;
}
