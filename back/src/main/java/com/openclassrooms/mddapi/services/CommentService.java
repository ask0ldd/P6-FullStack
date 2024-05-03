package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.services.interfaces.ICommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAll(){
        return commentRepository.findAll();
    }

    /*public List<Comment> getAllByArticle(Article article){
        return commentRepository.findByArticle(article);
    }*/

    public Comment create(Comment comment){
        return commentRepository.save(comment);
    }

    /*
    public Comment create(Comment comment, Long ArticleId){
        Comment comment = commentRepository.save(comment);
        Article.getById;
        Article.setComment(comment);
        return comment;
    }
     */
}
