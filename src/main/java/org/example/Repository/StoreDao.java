package org.example.Repository;

import java.util.ArrayList;
import java.util.Optional;

import org.example.Entity.Member;
import org.example.Entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDao extends JpaRepository<Store, Integer> {
    // 판매자별 상품 검색
    ArrayList<Store> findByMember(Member member);
    void deleteByNum(Long num);

    Optional<Store> findByNum(Long num);
    
}
