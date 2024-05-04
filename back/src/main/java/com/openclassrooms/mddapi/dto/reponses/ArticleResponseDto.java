package com.openclassrooms.mddapi.dto.reponses;

import com.openclassrooms.mddapi.dto.partials.LimitedUserDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String content;
    private LimitedUserDto user;
    private List<LimitedComment> comments;
    private LimitedTopic topic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.comments = article.getComments().stream().map(LimitedComment::new).toList();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
        this.user = new LimitedUserDto(article.getUser());
        this.topic = new LimitedTopic(article.getTopic());
    }

    @Data
    @AllArgsConstructor
    private static class LimitedComment{
        private Long id;
        private LimitedUserDto user;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public LimitedComment(Comment comment){
            this.id = comment.getId();
            this.user = new LimitedUserDto(comment.getUser());
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.updatedAt = comment.getUpdatedAt();
        }
    }

    @Data
    @AllArgsConstructor
    private static class LimitedTopic{
        private Long id;
        private String name;
        private String description;

        public LimitedTopic(Topic topic){
            this.id = topic.getId();
            this.name = topic.getName();
            this.description = topic.getDescription();
        }
    }
}

