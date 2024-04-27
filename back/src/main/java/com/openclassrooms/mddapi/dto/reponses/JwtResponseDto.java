package com.openclassrooms.mddapi.dto.reponses;

import com.openclassrooms.mddapi.models.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JwtResponseDto {

    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private String token;

}
