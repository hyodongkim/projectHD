package org.example.ServiceImpl;

import org.example.Entity.Member;
import org.example.Repository.MemberRepository;
import org.example.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Long register(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }

    @Override
    public Member isMember(String userid, String password) {
        return memberRepository.findByUseridAndPassword(userid, password);
    }

    @Override
    public Optional<Member> findMember(String userid) {
        return memberRepository.findByUserid(userid);
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Page<Member> findMembers(String searchValue, Pageable pageable) {
        return memberRepository.findAllByUseridOrNameContaining(searchValue, searchValue, pageable);
    }

    @Override
    public void updateMember(Member member) {
        memberRepository.save(member);
    }
    @Override
    public void deleteMember(Long id) {
         memberRepository.deleteById(id);
    }
}