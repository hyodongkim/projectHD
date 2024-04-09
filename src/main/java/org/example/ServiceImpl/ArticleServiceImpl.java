package org.example.ServiceImpl;

import java.util.Collection;
import java.util.Collections;
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

    @Override
    public List<Article> findMembersId(Long member){

        if(member == null){
            System.out.println("Hello World!");
            return Collections.emptyList();
        }

        return articleRepository.findMembersId(member);
    }

    @Override
    public List<Article> findByAdminArticle(){
        return articleRepository.findByAdminArticle();
    }

    @Override
    public void deleteArticleMember(Long member){
        articleRepository.deleteArticleMember(member);
    }


    @Override
    public Optional<Article> findByMember(Long member) {

        Optional<Article> article = articleRepository.findByMember(member);
        if(article.isEmpty()){
            return Optional.empty();
        }

        return articleRepository.findByMember(member);
    }
}
