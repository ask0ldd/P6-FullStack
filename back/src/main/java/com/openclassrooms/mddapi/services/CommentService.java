package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.ArticleNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.ICommentService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * {@inheritDoc}
     * Retrieves all comments.
     */
    public List<Comment> getAll(){
        return commentRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * Creates a new comment.
     */
    public Comment create(Comment comment){
        return commentRepository.save(comment);
    }

    /**
     * {@inheritDoc}
     * Creates a new comment for a given article.
     */
    public Comment create(Principal principal, Long articleId, String commentBody){
        // retrieves the logged user email
        String authorEmail = principal.getName();
        // retrieves the comment author user the logged user email
        User commentAuthor = userRepository.findByEmail(authorEmail).orElseThrow(() -> new UserNotFoundException("User not found."));
        // retrieves the article the comment is related to
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException("Article not found."));
        Comment newComment = Comment.builder().content(commentBody).user(commentAuthor).article(article).build();
        return commentRepository.save(newComment);
    }

}
