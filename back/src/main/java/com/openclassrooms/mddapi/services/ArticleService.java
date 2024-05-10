package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.ArticleNotFoundException;
import com.openclassrooms.mddapi.exceptions.BadRequestException;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.services.interfaces.IArticleService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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

    public List<Article> getAllForUser(String username) {
        // !!! should be filtered
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            throw new ArticleNotFoundException("Can't find any article");
        }
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
}
