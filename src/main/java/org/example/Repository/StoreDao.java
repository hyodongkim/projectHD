package org.example.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
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

    @Modifying
    @Transactional
    @Query(value="DELETE FROM Store s WHERE s.storeFilename=:storeFilename")
    public void deleteByStoreFilename(@Param("storeFilename") String storeFilename);


    @Modifying
    @Transactional
    @Query(value="Delete From Store s Where s.originFilename Is Null")
    public void deleteEmptyName();

    Optional<Store> findByNum(Long num);


//    @Query(value="SELECT  s.num"
//            + "		FROM Store s"
//            + "   			 INNER JOIN Member m"
//            + "   							 ON s.member_id = m.member_id"
//            + "		WHERE  s.member_id = :member_id",nativeQuery = true)
//    List<Long> findStore(@Param("member_id") Long id);
    @Query(value="SELECT s.*"
            + "		FROM store s"
            + "   			 INNER JOIN member m"
            + "   							 ON s.member_id = m.member_id"
            + "		WHERE s.num = :num",nativeQuery = true)
    List<Store> findStore(@Param("num") Long num);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM Store s WHERE s.member_id=:member_id",nativeQuery=true)
    public void deleteByNums(@Param("member_id") Long id);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO Store VALUES(:#{#store.num},:#{#store.originFilename},:#{#store.storeFilename},:#{#store.member_id})",nativeQuery=true)
    public void updateStore(@Param("store") Store store);
}
