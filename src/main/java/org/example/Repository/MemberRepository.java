package org.example.Repository;

import org.example.Entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    public Member findByIdAndPassword(String id, String password);

    // 아이디 또는 이름에 의한 검색 - 목록 페이징 처리
    Page<Member> findAllByIdOrNameContaining(String id, String name, Pageable pageable);
}
