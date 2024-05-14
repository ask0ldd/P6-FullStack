package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.models.Article;

import java.util.List;

public interface IArticleService {

    /**
     * Retrieves an Article by its unique identifier.
     *
     * @param id the unique identifier of the Article
     * @return the Article with the specified id
     * @throws ResourceNotFoundException if no Article is found with the given id
     */
    public Article getById(Long id);

    /**
     * Retrieves all Articles for the specified user.
     *
     * @param userEmail the email address of the user
     * @return a list of Articles for the specified user
     * @throws UserNotFoundException if no User is found with the given email
     * @throws TopicNotFoundException if the user is not subscribed to any Topics
     * @throws ArticleNotFoundException if no Articles are found for the user's subscribed Topics
     */
    public List<Article> getAllForUser(String userEmail);

    /**
     * Retrieves all Articles for the specified user, ordered by the specified direction.
     *
     * @param direction the order direction, either "asc" for ascending or "desc" for descending
     * @param userEmail the email address of the user
     * @return a list of Articles for the specified user, ordered by the specified direction
     * @throws UserNotFoundException if no User is found with the given email
     * @throws TopicNotFoundException if the user is not subscribed to any Topics
     * @throws BadRequestException if an invalid order direction is specified
     * @throws ArticleNotFoundException if no Articles are found for the user's subscribed Topics
     */
    public List<Article> getAllForUserOrderedByDate(String direction, String userEmail);

    /**
     * Creates a new Article.
     *
     * @param article the Article to be created
     * @return the created Article
     */
    public Article create(Article article);

    /**
     * Creates a new Article with the specified details.
     *
     * @param emailAuthor the email address of the Article's author
     * @param parentTopicId the unique identifier of the parent Topic
     * @param articleTitle the title of the Article
     * @param articleContent the content of the Article
     * @return the created Article
     * @throws UserNotFoundException if no User is found with the given email
     * @throws TopicNotFoundException if no Topic is found with the given id
     */
    public Article create(String emailAuthor, Long parentTopicId, String articleTitle, String articleContent);
}
