package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.payloads.NewArticlePayloadDto;
import com.openclassrooms.mddapi.dto.payloads.NewCommentPayloadDto;
import com.openclassrooms.mddapi.dto.reponses.ArticleResponseDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, UserService userService, TopicService topicService, CommentService commentService){
        this.articleService = articleService;
        this.commentService = commentService;
    }

    /**
     * Retrieves an article by its unique identifier.
     *
     * @param id the unique identifier of the article
     * @return A ResponseEntity containing the ArticleResponseDto representing the retrieved article
     */
    @GetMapping("article/{id}")
    public ResponseEntity<ArticleResponseDto> findById(@PathVariable("id") String id) {
        Article article = articleService.getById(Long.valueOf(id));
        return ResponseEntity.ok().body(new ArticleResponseDto(article));
    }

    /**
     * Retrieves a list of articles ordered by date, based on the specified order and the currently authenticated user.
     *
     * @param order    the order to sort the articles by, either "asc" for ascending or "desc" for descending
     * @param principal the currently authenticated user
     * @return A ResponseEntity containing a list of ArticleResponseDto objects representing the sorted articles
     */
    @GetMapping("articles/{order}") // https://www.baeldung.com/exception-handling-for-rest-with-spring : solution 3
    public ResponseEntity<List<ArticleResponseDto>> findAllOrderedByDate(@PathVariable("order") String order, Principal principal) {
        List<Article> articles = articleService.getAllForUserOrderedByDate(order, principal.getName());
        List<ArticleResponseDto> responsesDtoList = articles.stream()
                .map(ArticleResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(responsesDtoList);
    }

    /**
     * Retrieves all articles for the authenticated user.
     *
     * @param principal the authenticated user's principal object, which contains the username
     * @return A ResponseEntity containing a list of ArticleResponseDto objects representing the user's articles
     */
    @GetMapping("articles")
    public ResponseEntity<List<ArticleResponseDto>> findAllForUser(Principal principal) {
        List<Article> articles = articleService.getAllForUser(principal.getName());

        List<ArticleResponseDto> responsesDtoList = articles.stream()
                .map(ArticleResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(responsesDtoList);
    }

    /**
     * Handles the creation of a new article.
     *
     * @param articleRequest the request payload containing the article details
     * @param principal the authenticated user principal
     * @return A ResponseEntity with an empty body and a 200 status code.
     */
    @PostMapping("article")
    public ResponseEntity<Void> postArticle(@Valid @RequestBody NewArticlePayloadDto articleRequest, Principal principal){
        String emailAuthor = principal.getName();
        String parentTopicId = articleRequest.getTopicId();
        String articleTitle = articleRequest.getTitle();
        String articleContent = articleRequest.getContent();

        articleService.create(emailAuthor, Long.parseLong(parentTopicId), articleTitle, articleContent);

        return ResponseEntity.ok().build();
    }

    /**
     * Handles the POST request for creating a new comment on an article.
     *
     * @param commentRequest The request payload containing the comment data.
     * @param principal      The authenticated principal (user) making the request.
     * @return A ResponseEntity with an HTTP status code indicating the result of the operation.
     **/
    @PostMapping("article/{id}/comment")
    public ResponseEntity<Void> postComment(@Valid @RequestBody NewCommentPayloadDto commentRequest, Principal principal) {
        Long parentArticleId = Long.parseLong(commentRequest.getArticleId());
        String commentBody = commentRequest.getComment();
        commentService.create(principal, parentArticleId, commentBody);
        return ResponseEntity.ok().build();
    }
}
