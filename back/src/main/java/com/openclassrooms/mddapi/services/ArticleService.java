package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Article getById(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    public List<Article> getAll() {return articleRepository.findAll();}

    public List<Article> getAllByDateAsc() {return articleRepository.findAll(Sort.by("date"));}

    public List<Article> getAllByDateDesc() {return articleRepository.findAll(Sort.by("date").descending());}

    public Article create(Article article){
        return articleRepository.save(article);
    }
}
