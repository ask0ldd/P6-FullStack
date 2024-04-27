package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewArticlePayloadDto {

    @NotBlank
    private String topicId;

    @NotBlank
    @Size(min = 3, max = 60)
    private String title;

    @NotBlank
    @Size(min = 3, max = 400)
    private String content;
}
