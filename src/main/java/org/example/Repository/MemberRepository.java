package org.example.Repository;

import jakarta.transaction.Transactional;
import org.example.Entity.Member;
import org.example.Entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public static final Map<Long, Member> members = new HashMap<>();

    public Member findByIdAndPassword(String userid, String password);

    // 아이디 또는 이름에 의한 검색 - 목록 페이징 처리
    public Page<Member> findAllByUseridOrNameContaining(String userid, String name, Pageable pageable);

    public Optional<Member> findById(Long id);

    public Member findByUseridAndPassword(String userid, String password);

    public Optional<Member> findByUserid(String userid);
    @Modifying
    @Transactional
    public void deleteById(Long id);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO Member VALUES(:#{#member.member_id},:#{#member.member_userid},:#{#member.member_password},:#{#member.member_email},:#{#member.member_name},:#{#member.member_sex},:#{#member.member_age},:#{#member.member_birth},:#{#member.member_first},:#{#member.member_introduction})",nativeQuery=true)
    void updateMember(@Param("member") Member member);
}
