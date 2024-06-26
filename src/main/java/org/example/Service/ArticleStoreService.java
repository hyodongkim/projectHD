package org.example.Service;

import org.example.Entity.Article;
import org.example.Entity.ArticleStore;

public interface ArticleStoreService {
    void delArticle(String storeFilename);

    void save(ArticleStore articleStore);

    void deleteEmptyName();

    void insertArticleStore(ArticleStore articleStore);

    void updateArticleStore(ArticleStore articleStore);

    void deleteArticleStoreMember(Long articleId);
}
