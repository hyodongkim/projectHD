package org.example.Repository;

import jakarta.transaction.Transactional;
import org.example.Entity.Article;
import org.example.Entity.SeeCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeeCatalogRepository extends JpaRepository<SeeCatalog, Long> {
    @Query(value="Select Count(1) From See_Catalog s where s.see_info_member_id=:memberId and s.see_info_article_id=:articleId and see_info_click Is Not Null", nativeQuery = true)
    public Integer findBySeeInfoClickCount(@Param("articleId") Long articleId, @Param("memberId") Long memberId);

    @Query(value="Select Count(1) From See_Catalog s where s.see_info_member_id=:memberId and s.see_info_article_id=:articleId and see_info_hit Is Not Null", nativeQuery = true)
    public Integer findBySeeInfoHitCount(@Param("articleId") Long articleId, @Param("memberId") Long memberId);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO See_Catalog s(s.see_id,s.see_info_article_id,s.see_info_member_id) VALUES(see_catalog_seq.NEXTVAL,:articleId,:memberId)", nativeQuery = true)
    public void info(@Param("articleId") Long articleId, @Param("memberId") Long memberId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE See_Catalog s set s.see_info_click = '1' where s.see_info_member_id=:memberId and s.see_info_article_id=:articleId", nativeQuery = true)
    public void info_click(@Param("articleId") Long articleId, @Param("memberId") Long memberId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE See_Catalog s set s.see_info_hit = '1' where s.see_info_member_id=:memberId and s.see_info_article_id=:articleId", nativeQuery = true)
    public void info_hit(@Param("articleId") Long articleId, @Param("memberId") Long memberId);

}
