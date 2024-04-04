package org.example.Repository;

import jakarta.transaction.Transactional;
import org.example.Entity.Article;
import org.example.Entity.ArticleStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    public void deleteByStoreFilename(@Param("storeFilename") String storeFilename);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO article_store VALUES(article_store_seq.NEXTVAL,:#{#artisto.originFilename},:#{#artisto.storeFilename},:#{#artisto.article})",nativeQuery=true)
    void insertArticleStore(@Param("artisto") ArticleStore articleStore);

    @Modifying
    @Transactional
    @Query(value="UPDATE article_store a set a.article_num=article_store_seq.NEXTVAL, a.originFilename=:#{#artisto.originFilename}, a.storeFilename=:#{#artisto.storeFilename} WHERE a.article=:#{#artisto.article}",nativeQuery=true)
    public void updateArticleStore(@Param("artisto") ArticleStore articleStore);

    @Modifying
    @Transactional
    @Query(value="Delete FROM article_store s Where EXISTS(SELECT * FROM article a WHERE s.article_id=a.article_id and a.member=:articleId)",nativeQuery=true)
    public void deleteArticleStoreMember(@Param("articleId") Long articleId);
}
