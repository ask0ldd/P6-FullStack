package com.openclassrooms.mddapi.dto.partials;

import com.openclassrooms.mddapi.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LimitedUserDto {
    private Long id;
    private String username;

    public LimitedUserDto(User user){
        this.id = user.getId();
        this.username = user.getName();
    }
}
