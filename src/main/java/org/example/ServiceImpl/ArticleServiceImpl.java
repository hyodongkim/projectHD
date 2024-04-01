package org.example.ServiceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Entity.Article;
import org.example.Entity.ArticleStore;
import org.example.Repository.ArticleRepository;
import org.example.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Page<Article> findArticles(String searchValue, Pageable pageable){

        return articleRepository.findAllByNameOrContentContaining(searchValue,searchValue,pageable);
    }

    @Override
    public Optional<Article> findArticle(Long articleId) {
        return articleRepository.findByArticleId(articleId);
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteByArticleId(articleId);
    }

    @Override
    public void deleteArticles(Long articleId) {
        articleRepository.deleteByArticleId(articleId);
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

//    @Override
//    public void deleteEmptyValue(){
//        articleRepository.deleteEmptyValue();
//    }

    @Override
    public void deleteAllImagesByArticleId(Long articleId){
        articleRepository.deleteAllImagesByArticleId(articleId);
    }
    @Override
    public void insertArticle(Article article){
        articleRepository.insertArticle(article);
    }
    @Override
    public void updateArticle(Article article){
        articleRepository.updateArticle(article);
    }
    @Override
    public void plusHitCount(Long articleId){
        articleRepository.plusHitCount(articleId);
    }

    @Override
    public void plusClickCount(Long articleId) {
        articleRepository.plusClickCount(articleId);
    }
}
