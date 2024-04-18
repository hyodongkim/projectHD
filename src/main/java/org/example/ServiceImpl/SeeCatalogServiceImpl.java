package org.example.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.example.Entity.SeeCatalog;
import org.example.Repository.SeeCatalogRepository;
import org.example.Service.SeeCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class SeeCatalogServiceImpl implements SeeCatalogService {

    @Autowired
    private SeeCatalogRepository seeCatalogRepository;

    @Override
    public void info(Long articleId, Long memberId){
        seeCatalogRepository.info(articleId,memberId);
    }
    @Override
    public void info_click(Long articleId, Long memberId){
        seeCatalogRepository.info_click(articleId,memberId);
    }
    @Override
    public void info_hit(Long articleId, Long memberId){
        seeCatalogRepository.info_hit(articleId,memberId);
    }

    @Override
    public Integer findBySeeInfoClickCount(Long articleId,Long memberId){

        if(seeCatalogRepository.findBySeeInfoClickCount(articleId,memberId)>0){
            return 1;
        }else{
            return 0;
        }

    }
    @Override
    public Integer findBySeeInfoHitCount(Long articleId,Long memberId){
        if(seeCatalogRepository.findBySeeInfoHitCount(articleId,memberId)>0){
            return 1;
        }else{
            return 0;
        }
    }

}
