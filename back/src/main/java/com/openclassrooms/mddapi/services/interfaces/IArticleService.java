package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.exceptions.ArticleNotFoundException;
import com.openclassrooms.mddapi.exceptions.BadRequestException;
import com.openclassrooms.mddapi.exceptions.TopicNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Article;

import java.util.List;

public interface IArticleService {

    /**
     * Retrieves an article by its unique identifier.
     *
     * @param id the unique identifier of the article
     * @return the article with the specified ID, or null if not found
     */
    public Article getById(Long id);

    /**
     * Retrieves all articles for a given user based on the topics they are subscribed to.
     *
     * @param userEmail The email address of the user for whom to retrieve articles.
     * @return A list of articles associated with the topics the user is subscribed to.
     * @throws UserNotFoundException If the user with the provided email address is not found.
     * @throws TopicNotFoundException If the user is not subscribed to any topic.
     * @throws ArticleNotFoundException If no articles are found for the user's subscribed topics.
     */
    public List<Article> getAllForUser(String userEmail);

    /**
     * Retrieves a list of articles for a given user, ordered by date in ascending or descending order.
     *
     * @param direction The order direction, either "asc" for ascending or "desc" for descending.
     * @param userEmail The email address of the user for whom to retrieve articles.
     * @return A list of articles ordered by date according to the specified direction.
     * @throws UserNotFoundException If the user with the provided email is not found.
     * @throws TopicNotFoundException If the user is not subscribed to any topic.
     * @throws BadRequestException If an invalid direction is provided.
     * @throws ArticleNotFoundException If no articles are found for the user's subscribed topics.
     */
    public List<Article> getAllForUserOrderedByDate(String direction, String userEmail);

    /**
     * Creates a new article in the system.
     *
     * @param article the article to be created
     * @return the created article, with its unique identifier set
     */
    public Article create(Article article);

    /**
     * Creates a new Article entity and persists it in the database.
     *
     * @param emailAuthor   The email address of the User who is the author of the Article.
     * @param parentTopicId The ID of the Topic under which the Article should be created.
     * @param articleTitle  The title of the Article.
     * @param articleContent The content of the Article.
     * @return The newly created and persisted Article entity.
     * @throws UserNotFoundException    If the User with the provided email address is not found.
     * @throws TopicNotFoundException   If the Topic with the provided ID is not found.
     */
    public Article create(String emailAuthor, Long parentTopicId, String articleTitle, String articleContent);
}
