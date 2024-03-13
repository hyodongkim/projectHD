package org.example.Repository;

import jakarta.transaction.Transactional;
import org.example.Entity.Article;
import org.example.Entity.ArticleStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleStoreRepository extends JpaRepository<ArticleStore, Long> {

    @Modifying
    @Transactional
    @Query(value="Delete From ArticleStore s Where s.originFilename Is Null")
    public void deleteEmptyName();

    @Modifying
    @Transactional
    @Query(value="DELETE FROM ArticleStore s WHERE s.storeFilename=:storeFilename")
    public void deleteByStoreFilename(String storeFilename);
}
