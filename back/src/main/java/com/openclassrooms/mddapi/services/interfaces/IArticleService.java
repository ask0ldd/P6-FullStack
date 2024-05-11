package com.openclassrooms.mddapi.services.interfaces;

import com.openclassrooms.mddapi.exceptions.TopicNotFoundException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.data.domain.Sort;

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
     * Retrieves all articles in the system.
     *
     * @return a list of all articles
     */
    public List<Article> getAll();

    /**
     * Retrieves all articles linked to a user with username.
     *
     * @return a list of all articles related to the user
     */
    public List<Article> getAllForUser(String username);

    /**
     * Retrieves all articles sorted in ascending order by their publication date.
     *
     * @return a list of all articles sorted by date in ascending order
     */
    public List<Article> getAllByDateAsc();

    /**
     * Retrieves all articles sorted in descending order by their publication date.
     *
     * @return a list of all articles sorted by date in descending order
     */
    public List<Article> getAllByDateDesc();

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
