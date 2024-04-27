package com.openclassrooms.mddapi.dto.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCommentPayloadDto {

    @NotBlank
    private String articleId;

    @NotBlank
    @Size(min = 3, max = 400)
    private String comment;
}
