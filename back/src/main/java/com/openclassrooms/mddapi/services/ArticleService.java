package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.IArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, TopicRepository topicRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * {@inheritDoc}
     * Retrieves an Article by its unique identifier.
     */
    public Article getById(Long id) throws ResourceNotFoundException {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("Can't find the article with id: " + id));
    }

    /**
     * {@inheritDoc}
     * Retrieves all Articles for the specified user.
     */
    public List<Article> getAllForUser(String userEmail) {
        // retrieve the user
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Can't find user with email : " + userEmail));
        // find all the topics the user is subscribed to
        List<Topic> topics = topicRepository.findAllByUsersContaining(user);
        // if the user is subscribed to no topics then return an empty array of articles
        if(topics.isEmpty()) return List.of(new Article[]{});
        List<Article> articles = articleRepository.findByTopicIn(topics);
        return articles;
    }

    /**
     * {@inheritDoc}
     * Retrieves all Articles for the specified user, ordered by the specified direction.
     */
    public List<Article> getAllForUserOrderedByDate(String direction, String userEmail) {
        // retrieve the user
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Can't find user with email : " + userEmail));
        // find all the topics the user is subscribed to
        List<Topic> topics = topicRepository.findAllByUsersContaining(user);
        // if the user is subscribed to no topics then return an empty array of articles
        if(topics.isEmpty()) return List.of(new Article[]{});
        List<Article> articles = null;
        // find all the articles linked to the previous topics in the requested order
        if(Objects.equals(direction, "asc")) articles = articleRepository.findByTopicInOrderByUpdatedAtAsc(topics);
        if(Objects.equals(direction, "desc")) articles = articleRepository.findByTopicInOrderByUpdatedAtDesc(topics);
        return articles;
    }

    /**
     * {@inheritDoc}
     * Creates a new Article.
     */
    public Article create(Article article){
        return articleRepository.save(article);
    }

    /**
     * {@inheritDoc}
     * Creates a new Article with the specified details.
     */
    public Article create(String emailAuthor, Long parentTopicId, String articleTitle, String articleContent){
        // check if the author exists
        User author = userRepository.findByEmail(emailAuthor).orElseThrow(() -> new UserNotFoundException("Article's author not found."));
        // check if the topic exists
        Topic parentTopic = topicRepository.findById(parentTopicId).orElseThrow(() -> new TopicNotFoundException("Topic not found."));
        // create a new article related to both
        Article newArticle = Article.builder()
                .user(author)
                .topic(parentTopic)
                .title(articleTitle)
                .content(articleContent)
                .build();
        return articleRepository.save(newArticle);
    }
}
