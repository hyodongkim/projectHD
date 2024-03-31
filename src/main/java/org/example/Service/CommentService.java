package org.example.Service;

import org.example.Entity.Comment;
import org.example.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentService {

    public Page<Comment> findByArticleId(Long articleId, Pageable pageable);

    public void save(Comment comment);

    public void insertArticle(Comment comment);
}
