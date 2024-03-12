package org.example.ServiceImpl;

import java.util.List;

import org.example.Entity.Board;
import org.example.Repository.BoardRepository;
import org.example.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class BoardServiceImpl implements BoardService {
        @Autowired
        private BoardRepository boardRepository;
        @Override
        public List<Board> findAll() {
            return boardRepository.findAll(Sort.by(Sort.Direction.ASC,"boardId"));

        }
}
