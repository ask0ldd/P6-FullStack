package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewArticlePayloadDto {

    @NotBlank(message = "A topic needs to be selected")
    private String topicId;

    @NotBlank
    @Size(min = 3, max = 60, message = "Title length must be between 3 and 60")
    private String title;

    @NotBlank
    @Size(min = 3, max = 400, message = "Content length must be between 3 and 400")
    private String content;
}
