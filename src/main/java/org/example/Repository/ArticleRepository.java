package org.example.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.example.Entity.Article;
import org.example.Entity.Member;
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
}
