package org.example.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.example.Entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

    public Page<Article> findAllByNameOrContentContaining(String name, String content, Pageable pageable);

    public Optional<Article> findByArticleId(Long articleId);

    @Modifying
    @Transactional
    public void deleteByArticleId(Long articleId);

//    @Modifying
//    @Transactional
//    @Query(value="Delete From Article a Where a.groupNo Is Null")
//    void deleteEmptyValue();

    @Modifying
    @Transactional
    @Query(value="DELETE FROM article_store s WHERE s.article_id=:articleId",nativeQuery=true)
    public void deleteAllImagesByArticleId(@Param("articleId") Long articleId);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO Article a VALUES(a.article_seq.NEXTVAL,:#{#arti.name},:#{#arti.subject},:#{#arti.content},:#{#arti.day},:#{#arti.member})",nativeQuery=true)
    public void insertArticle(@Param("arti") Article article);

    @Modifying
    @Transactional
    @Query(value="UPDATE article a set a.article_id=article_seq.NEXTVAL,a.name=:#{#article.name},a.subject=:#{#article.subject},a.content=:#{#article.content},a.day=:#{#article.day} where a.member=:#{#article.member}",nativeQuery=true)
    public void updateArticle(@Param("article") Article article);

    @Modifying
    @Transactional
    @Query(value="UPDATE article a set a.clickcount=a.clickcount+1 where a.article_id=:articleId",nativeQuery=true)
    public void plusClickCount(@Param("articleId") Long articleId);

    @Modifying
    @Transactional
    @Query(value="UPDATE article a set a.hitcount=a.hitcount+1 where a.article_id=:articleId",nativeQuery=true)
    public void plusHitCount(@Param("articleId") Long articleId);


    @Query(value="SELECT * FROM Article a join member m on m.member_id=a.member WHERE a.member=:id and a.name=m.member_name",nativeQuery=true)
    public List<Article> findMembersId(@Param("id") Long member);

    @Query(value="SELECT Count(1) FROM Article a join member m on m.member_id=a.member WHERE a.member=:id and a.name=m.member_name",nativeQuery = true)
    public Integer countMembersId(@Param("id") Long member);

    @Query(value="select * from article a join member m on m.member_id=a.member where m.member_job=1 and a.name=m.member_name",nativeQuery=true)
    public List<Article> findByAdminArticle();

    @Query(value="select Count(1) from article a join member m on m.member_id=a.member where m.member_job=1 and a.name=m.member_name",nativeQuery = true)
    public Integer countAdminArticle();

    @Modifying
    @Transactional
    @Query(value="DELETE FROM article a WHERE a.member=:member",nativeQuery=true)
    public void deleteArticleMember(@Param("member") Long member);

    @Modifying
    @Transactional
    @Query(value="UPDATE article a set a.viewSetCountArticle='yes' where a.article_id=:articleId",nativeQuery=true)
    public void changeCountYes(@Param("articleId") Long articleId);

    @Modifying
    @Transactional
    @Query(value="UPDATE article a set a.viewSetHitArticle='yes' where a.article_id=:articleId",nativeQuery=true)
    public void changeHitYes(@Param("articleId") Long articleId);

    @Query(value="SELECT a.viewSetCountArticle FROM article a WHERE a.article_id=:articleId",nativeQuery=true)
    public String isCountYes(@Param("articleId") Long articleId);
    @Query(value="SELECT a.viewSetHitArticle FROM article a WHERE a.article_id=:articleId",nativeQuery=true)
    public String isHitYes(@Param("articleId") Long articleId);
}
