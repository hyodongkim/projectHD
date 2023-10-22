package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceImpl implements service {

    @Autowired
    private repository Repository;

    @Override
    public List<entity> findAll() {
        return Repository.findAll();
    }
}