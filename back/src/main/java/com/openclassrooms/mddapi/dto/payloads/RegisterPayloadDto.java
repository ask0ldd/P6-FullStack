package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterPayloadDto {
    @NotBlank(message = "Username can't be blank.")
    @Size(min = 6, max = 20, message = "Username length must be between 6 & 20.")
    private String username;

    @NotBlank(message = "Email can't be blank.")
    @Size(max = 50, message = "Email length can't exceed 50.")
    @Email(message = "Not a valid email format.")
    private String email;

    @NotBlank(message = "Password can't be blank.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$", message = "Password must be at least 8 characters long and contain at least one number, one lowercase letter, one uppercase letter, and one special character.")
    @Size(min = 8, max = 40, message = "Password length must be between 8 & 40.")
    private String password;
}
