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
    public String register(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Member isMember(String id, String password) {
        return memberRepository.findByIdAndPassword(id, password);
    }

    @Override
    public Optional<Member> findMember(String id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Page<Member> findMembers(String searchValue, Pageable pageable) {
        return memberRepository.findAllByIdOrNameContaining(searchValue, searchValue, pageable);
    }

    @Override
    public String update(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }
}