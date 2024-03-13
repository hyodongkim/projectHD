package org.example.Service;

import org.example.Entity.ArticleStore;

public interface ArticleStoreService {
    void save(ArticleStore articleStore);

    void deleteEmptyName();
}
