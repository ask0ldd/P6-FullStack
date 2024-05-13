package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTopicIn(List<Topic> topics);
    List<Article> findByTopicInOrderByUpdatedAtAsc(List<Topic> topics);
    List<Article> findByTopicInOrderByUpdatedAtDesc(List<Topic> topics);
}
