package org.example.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.example.Dto.WriteBoardForm;
import org.example.Entity.Article;
import org.example.Entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    public Page<Article> findArticles(String search, Pageable pageable);

    Optional<Article> findArticle(Long articleId);

    void deleteArticle(Long articleId);

    void save(Article article);

//    void deleteEmptyValue();

    void deleteArticles(Long articleId);

    void deleteAllImagesByArticleId(Long articleId);

    void insertArticle(Article article);

    public void updateArticle(Article article);

    public void plusHitCount(Long articleId);

    public void plusClickCount(Long articleId);

    public List<Article> findMembersName(String article);
}