package org.example.Service;

import java.util.ArrayList;

import org.example.Dto.StoreDto;
import org.example.Entity.Member;
import org.example.Entity.Store;
import org.example.Repository.StoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StoreService {
    @Autowired
    private StoreDao dao;

    public void save(StoreDto dto) {
        dao.save(new Store(dto.getNum(), dto.getMember()));
    }

    // 상품 전체 검색
    public ArrayList<StoreDto> getAll(){
        ArrayList<Store> list = (ArrayList<Store>) dao.findAll();
        ArrayList<StoreDto> list2 = new ArrayList<StoreDto>();
        for (Store s:list) { // list 길이만큼 반복하며 값을 하나씩 꺼내서 변수 s에 담음.
            list2.add(new StoreDto(s.getNum(), s.getMember(),
                    null)); // multipart는 db에 안넣을 거니까 Null.

        }
        return list2;
    }

    // 판매자로 검색
    public ArrayList<StoreDto> getByMember(String member) {
        Member vo = new Member();
        ArrayList<Store> list = (ArrayList<Store>) dao.findByMember(vo);
        ArrayList<StoreDto> list2 = new ArrayList<StoreDto>();
        for (Store s:list) { // list 길이만큼 반복하며 값을 하나씩 꺼내서 변수 s에 담음.
            list2.add(new StoreDto(s.getNum(), s.getMember(),
                    null)); // multipart는 db에 안넣을 거니까 Null.

        }
        return list2;
    }

    // 상품번호로 검색
    public StoreDto getShop(Long num) {
        Store s = dao.findByNum(num).orElse(null);
        if(s==null) {
            return null;
        }
        return new StoreDto(s.getNum(), s.getMember(),
                null);
    }

    // 삭제
    public void delShop(Long num) {
        dao.deleteByNum(num);
    }

}