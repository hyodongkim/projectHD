package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.Entity.Article;
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

    public List<Article> findMembersId(Long member);

    public Integer countMembersId(Long member);

    public List<Article> findByAdminArticle();

    public Integer countAdminArticle();

    public void deleteArticleMember(Long member);

    public void changeHitYes(Long articleId);

    public void changeCountYes(Long articleId);

    public Long isCountYes(Long articleId);
    public Long isHitYes(Long articleId);
}