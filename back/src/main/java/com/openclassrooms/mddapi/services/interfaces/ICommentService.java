package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.models.Comment;

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
}
