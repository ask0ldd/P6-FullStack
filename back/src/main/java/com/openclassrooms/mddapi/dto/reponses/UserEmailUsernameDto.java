package com.openclassrooms.mddapi.dto.reponses;

import com.openclassrooms.mddapi.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEmailUsernameDto {
    private String username;
    private String email;

    public UserEmailUsernameDto(User user){
        this.username = user.getName();
        this.email = user.getEmail();
    }
}
