package org.example.Service;

import org.example.Entity.ArticleStore;

public interface ArticleStoreService {
    void delArticle(String storeFilename);

    void save(ArticleStore articleStore);

    void deleteEmptyName();
}
