package org.example.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.example.Entity.Article;
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
    public void registerArticle(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void deleteEmptyValue(){
        articleRepository.deleteEmptyValue();
    }
}
