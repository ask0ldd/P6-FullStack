package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.IArticleService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Article getById(Long id) throws ResourceNotFoundException {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("Can't find the article with id: " + id));
    }

    public List<Article> getAll() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            throw new ArticleNotFoundException("Can't find any article");
        }
        return articles;
    }

    public List<Article> getAllForUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User can't be found."));
        List<Topic> topics = topicRepository.findAllByUsersContaining(user);
        System.out.println(topics);
        if(topics.isEmpty()) throw new TopicNotFoundException("User subscribed to no topic.");
        /*List<Long> allTopicsIds = topics.stream()
                .map(Topic::getId)
                .toList();*/
        // List<Article> articles = articleRepository.findByTopicIn(topics);
        List<Article> articles = articleRepository.findAll();
        System.out.println(articles);
        if (articles.isEmpty()) throw new ArticleNotFoundException("Can't find any article");
        return articles;
    }

    public List<Article> getAllByDateAsc() {
        List<Article> articles = articleRepository.findAll(Sort.by("updatedAt"));
        if (articles.isEmpty()) {
            throw new ArticleNotFoundException("Can't find any article");
        }
        return articles;
    }

    public List<Article> getAllByDateDesc() {
        List<Article> articles = articleRepository.findAll(Sort.by("updatedAt").descending());
        if (articles.isEmpty()) {
            throw new ArticleNotFoundException("Can't find any article");
        }
        return articles;
    }

    public Article create(Article article){
        return articleRepository.save(article);
    }

    public Article create(String emailAuthor, Long parentTopicId, String articleTitle, String articleContent){
        User author = userRepository.findByEmail(emailAuthor).orElseThrow(() -> new UserNotFoundException("Article's author not found."));
        Topic parentTopic = topicRepository.findById(parentTopicId).orElseThrow(() -> new TopicNotFoundException("Topic not found."));
        Article newArticle = Article.builder()
                .user(author)
                .topic(parentTopic)
                .title(articleTitle)
                .content(articleContent)
                .build();
        return articleRepository.save(newArticle);
    }
}
