package org.example.Repository;

import jakarta.transaction.Transactional;
import org.example.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Page<Comment> findByArticleId(@Param("article_id") Long article_id, Pageable pageable);

//    @Modifying
//    @Transactional
//    @Query(value="INSERT INTO comments(comment_id,writer,content,day,articleId) VALUES(comment_seq.NEXTVAL,:#{#comment.writer},:#{#comment.content},:#{#comment.day},:#{#comment.articleId})",nativeQuery=true)
//    public void insertArticle(@Param("comment") Comment comment);
}
