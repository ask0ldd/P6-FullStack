package com.openclassrooms.mddapi.dto.reponses;

import com.openclassrooms.mddapi.dto.partials.LimitedUserDto;
import com.openclassrooms.mddapi.models.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponseDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<LimitedUserDto> users;

    public TopicResponseDto(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.description = topic.getDescription();
        this.createdAt = topic.getCreatedAt();
        this.updatedAt = topic.getUpdatedAt();
        this.users = topic.getUsers().stream().map(LimitedUserDto::new).toList();
    }

}