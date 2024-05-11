package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.exceptions.ArticleNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Comment;

import java.security.Principal;
import java.util.List;

public interface ICommentService {

    /**
     * Retrieves all comments.
     *
     * @return a list of all comments
     */
    List<Comment> getAll();

    /**
     * Creates a new comment.
     *
     * @param comment the comment to be created
     * @return the created comment
     */
    Comment create(Comment comment);

    /**
     * Creates a new comment for a given article.
     *
     * @param principal   the authenticated principal (user) creating the comment
     * @param articleId   the ID of the article to which the comment is being added
     * @param commentBody the content of the comment
     * @return the newly created comment
     * @throws UserNotFoundException    if the authenticated user is not found
     * @throws ArticleNotFoundException if the article is not found
     */
    Comment create(Principal principal, Long articleId, String commentBody);
}
