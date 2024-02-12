package org.example.Repository;

import org.example.Entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByIdAndPassword(String userid, String password);

    // 아이디 또는 이름에 의한 검색 - 목록 페이징 처리
    public Page<Member> findAllByUseridOrNameContaining(String userid, String name, Pageable pageable);

    public Optional<Member> findById(Long id);

    public Member findByUseridAndPassword(String userid, String password);

    public Optional<Member> findByUserid(String userid);

    @Modifying
    @Query(value="INSERT INTO member(id,userid,password,email,name,sex,age,birth,day,introduction)"
            + "VALUES(:#{#mem.id},:#{#mem.userid},:#{#mem.password},:#{#mem.email},:#{#mem.name},:#{#mem.sex},:#{#mem.age},:#{#mem.birth},:#{#mem.day},:#{#mem.introduction})",nativeQuery = true)
    public void registMember(@Param("mem") Member member);
}
