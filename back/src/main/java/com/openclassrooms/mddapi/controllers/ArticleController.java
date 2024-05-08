package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.payloads.NewArticlePayloadDto;
import com.openclassrooms.mddapi.dto.payloads.NewCommentPayloadDto;
import com.openclassrooms.mddapi.dto.reponses.ArticleResponseDto;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final TopicService topicService;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, UserService userService, TopicService topicService, CommentService commentService){
        this.articleService = articleService;
        this.userService = userService;
        this.topicService = topicService;
        this.commentService = commentService;
    }

    /**
     * Retrieves an article by its unique identifier.
     *
     * @param id the unique identifier of the article to retrieve
     * @return a ResponseEntity<ArticleResponseDto> containing the requested article,
     * all errors are dealt with into the GlobalExceptionHandler and the related service
     */
    @GetMapping("article/{id}")
    public ResponseEntity<ArticleResponseDto> findById(@PathVariable("id") String id) {
        Article article = articleService.getById(Long.valueOf(id));
        return ResponseEntity.ok().body(new ArticleResponseDto(article));
    }

    /**
     * Retrieves a list of articles ordered by date, either in ascending or descending order.
     *
     * @param order The order in which the articles should be retrieved, either "asc" for ascending or "desc" for descending.
     * @return A ResponseEntity containing a list of ArticleResponseDto objects representing the articles,
     * or a 404 Not Found response if no article is found,
     * or a 400 Bad Request response if the provided order variable isn't "asc" or "desc" in value
     */
    @GetMapping("articles/{order}") // https://www.baeldung.com/exception-handling-for-rest-with-spring : solution 3
    public ResponseEntity<?/*List<ArticleResponseDto>*/> findAllOrderedByDate(@PathVariable("order") String order) {
        // public ResponseEntity<?/*List<ArticleResponseDto>*/> findAllOrderedByDate(@RequestParam String order) {
        try {
            // order should be "asc" or "desc"
            if(!Objects.equals(order, "asc") && !Objects.equals(order, "desc")) return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // enum asc + desc a utiliser dans requestparam

            List<Article> articles;
            if(!Objects.equals(order, "asc")) {
                articles = articleService.getAllByDateAsc();
            } else {
                articles = articleService.getAllByDateDesc();
            }

            if (articles.isEmpty()) { // move to service
                throw new ResourceNotFoundException("No article found.");
            }

            List<ArticleResponseDto> responseDtoList = articles.stream()
                    .map(ArticleResponseDto::new)
                    .toList();

            return ResponseEntity.ok().body(responseDtoList);
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // should become article for subscriber/{id}
    @GetMapping("articles")
    public ResponseEntity<?> findAllForUser(Principal principal) {
        try {
            List<Article> articles = articleService.getAllForUser(principal.getName());

            if (articles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<ArticleResponseDto> responsesDtoList = articles.stream()
                    .map(ArticleResponseDto::new)
                    .toList();

            return ResponseEntity.ok().body(responsesDtoList);
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("article")
    public ResponseEntity<?> postArticle(@Valid @RequestBody NewArticlePayloadDto articleRequest, Principal principal){
        try {
            // retrieve the user from the principal so the new article will be linked to him
            String writerEmail = principal.getName();
            User newArticleWriter = userService.getByEmail(writerEmail);

            Topic newArticleTopic = topicService.getById(Long.parseLong(articleRequest.getTopicId()));
            Article newArticle = Article.builder()
                    .user(newArticleWriter)
                    .topic(newArticleTopic)
                    .title(articleRequest.getTitle())
                    .content(articleRequest.getContent())
            .build();
            articleService.create(newArticle);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("article/{id}/comment")
    public ResponseEntity<?> postComment(@Valid @RequestBody NewCommentPayloadDto commentRequest, Principal principal) {
        try {
            // retrieve the user from the principal so the new comment will be linked to him
            String writerEmail = principal.getName();
            System.out.println(writerEmail);
            User commentWriter = userService.getByEmail(writerEmail);
            // !!!! check if not found

            Article article = articleService.getById(Long.parseLong(commentRequest.getArticleId()));
            Comment newComment = Comment.builder().content(commentRequest.getComment()).user(commentWriter).article(article).build();
            commentService.create(newComment);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("\u001B[31m" + e + "\u001B[0m");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
