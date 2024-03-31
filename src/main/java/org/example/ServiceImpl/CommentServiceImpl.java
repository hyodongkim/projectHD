package org.example.ServiceImpl;

import org.example.Entity.Comment;
import org.example.Repository.CommentRepository;
import org.example.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<Comment> findByArticleId(Long ArticleId, Pageable pageable){
        return commentRepository.findByArticleId(ArticleId,pageable);
    }
    @Override
    public void save(Comment comment){
        commentRepository.save(comment);
    }

    @Override
    public void insertArticle(Comment comment){
        commentRepository.insertArticle(comment);
    }
}
