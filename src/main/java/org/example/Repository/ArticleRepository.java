package org.example.Repository;

import java.util.List;
import java.util.Optional;

import org.example.Entity.Article;
import org.example.Entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long>{

    public Page<Article> findAllByWriterOrContentContaining(String writer, String content, Pageable pageable);

    public Optional<Article> findByArticleId(Long articleId);

    public void deleteByArticleId(Long articleId);
}
