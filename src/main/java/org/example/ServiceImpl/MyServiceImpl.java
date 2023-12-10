package org.example.ServiceImpl;

import org.example.Entity.MyEntity;
import org.example.Repository.MyRepository;
import org.example.Service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MyServiceImpl implements MyService {

    @Autowired
    private MyRepository Repository;

    @Override
    public List<MyEntity> findAll() {
        return Repository.findAll();
    }
}