package org.example.ServiceImpl;

import org.example.Entity.Article;
import org.example.Entity.ArticleStore;
import org.example.Repository.ArticleStoreRepository;
import org.example.Service.ArticleStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleStoreServiceImpl implements ArticleStoreService {

    @Autowired
    private ArticleStoreRepository articleStoreRepository;

    @Override
    public void delArticle(String storeFilename){
        articleStoreRepository.deleteByStoreFilename(storeFilename);
    }

    @Override
    public void save(ArticleStore articleStore) {
        articleStoreRepository.save(articleStore);
    }

    @Override
    public void deleteEmptyName() {
        articleStoreRepository.deleteEmptyName();
    }

    @Override
    public void insertArticleStore(ArticleStore articleStore){
        articleStoreRepository.insertArticleStore(articleStore);
    }
    @Override
    public void updateArticleStore(ArticleStore articleStore){
        articleStoreRepository.updateArticleStore(articleStore);
    }

    @Override
    public void deleteArticleStoreMember(Long articleId){
        articleStoreRepository.deleteArticleStoreMember(articleId);
    }
}
