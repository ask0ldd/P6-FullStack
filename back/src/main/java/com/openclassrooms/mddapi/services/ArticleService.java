package com.openclassrooms.mddapi.services;

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

    public Article getById(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    public List<Article> getAll() {return articleRepository.findAll();}

    public List<Article> getAllByDateAsc() {return articleRepository.findAll(Sort.by("updatedAt"));}

    public List<Article> getAllByDateDesc() {return articleRepository.findAll(Sort.by("updatedAt").descending());}

    public Article create(Article article){
        return articleRepository.save(article);
    }
}
