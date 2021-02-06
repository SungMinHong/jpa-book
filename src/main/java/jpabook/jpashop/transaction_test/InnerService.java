package jpabook.jpashop.transaction_test;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.NEVER)
@Service
@RequiredArgsConstructor
public class InnerService {
    private final MemberRepository memberRepository;

    public void call2() {
        Member member = new Member();
        member.setName("InnerService");
        memberRepository.save(member);
        throw new RuntimeException("unchecked exception!");
    }
}
