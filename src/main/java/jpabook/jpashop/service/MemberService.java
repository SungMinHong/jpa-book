package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    
    private final MemberRepository repository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(final Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        repository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(final Member member) {
        final var members = repository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    
    public List<Member> findMembers() {
        return repository.findAll();
    }
    
    public Member findOne(final Long memberId) {
        return repository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        final var member = repository.findOne(id);
        member.setName(name);
    }
}
