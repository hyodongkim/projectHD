package org.example.Service;

import java.util.List;

import org.example.Entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    public Page<Article> findArticles(String search, Pageable pageable);
}