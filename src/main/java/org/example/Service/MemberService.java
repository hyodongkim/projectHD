package org.example.Service;

import org.example.Entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    /**
     * 회원가입
     */
    public void register(Member member);

    /**
     * 회원 인증
     */
    public Member isMember(String userid, String password);

    /**
     * 회원 상세
     */
    public Optional<Member> findMember(Long id);

    /** 전체 회원 조회 */
    public List<Member> findMembers();

    /** 검색, 페이징 처리 회원 조회 */
    public Page<Member> findMembers(String searchValue, Pageable pageable);

    public void updateMember(Member member);

    public void deleteMember(Long id);

}
